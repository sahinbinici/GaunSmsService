package com.sms.gaunsmsservice.service;

import com.sms.gaunsmsservice.dto.RequestDto;
import com.sms.gaunsmsservice.dto.ResponseDto;
import com.sms.gaunsmsservice.dto.SmsApiResponse;
import com.sms.gaunsmsservice.entity.Request;
import com.sms.gaunsmsservice.entity.Response;
import com.sms.gaunsmsservice.repository.RequestRepository;
import com.sms.gaunsmsservice.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SmsService {
    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;
    private final SmsClient smsClient;

    public SmsService(RequestRepository requestRepository, ResponseRepository responseRepository, SmsClient smsClient) {
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.smsClient = smsClient;
    }

    public ResponseDto sendSms(RequestDto requestDto) {
        // Save request to database
        Request request = new Request();
        request.setGsm(requestDto.getGsm());
        request.setMsg(requestDto.getMsg());
        request.setSource(requestDto.getSource());
        request.setMessageDate(LocalDateTime.now());
        requestRepository.save(request);

        // Send SMS via API
        SmsApiResponse apiResponse = smsClient.sendSms(requestDto);

        // Build response DTO
        ResponseDto responseDto = new ResponseDto();
        if(apiResponse != null && apiResponse.isSuccess()) {
            responseDto.setStatus(true);
            responseDto.setStatusDesc(apiResponse.getDescription() != null ? apiResponse.getDescription() : "SMS Gönderildi");
            responseDto.setMsgID(apiResponse.getId() != null ? String.valueOf(apiResponse.getId()) : null);
        } else {
            responseDto.setStatus(false);
            responseDto.setStatusDesc(apiResponse != null && apiResponse.getDescription() != null 
                ? apiResponse.getDescription() 
                : "Numara Hatası veya SMS Gönderilemedi veya Kredi Yok");
            responseDto.setMsgID(null);
        }

        // Save response to database
        Response response = new Response();
        response.setMessageID(responseDto.getMsgID());
        response.setStatus(responseDto.isStatus());
        response.setStatusDesc(responseDto.getStatusDesc());
        response.setResponseDate(LocalDateTime.now());
        responseRepository.save(response);

        return responseDto;
    }
}

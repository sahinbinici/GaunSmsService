package com.sms.gaunsmsservice.service;

import com.sms.gaunsmsservice.dto.RequestDto;
import com.sms.gaunsmsservice.dto.ResponseDto;
import com.sms.gaunsmsservice.entity.Request;
import com.sms.gaunsmsservice.entity.Response;
import com.sms.gaunsmsservice.repository.RequestRepository;
import com.sms.gaunsmsservice.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmsService {
    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;

    public SmsService(RequestRepository requestRepository, ResponseRepository responseRepository) {
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
    }

    public ResponseDto sendSms(RequestDto requestDto) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        Request request =new Request();
        request.setGsm(requestDto.getGsm());
        request.setMsg(requestDto.getMsg());
        request.setSource(requestDto.getSource());
        requestRepository.save(request);
        if(SmsClient.sendSms(requestDto) != null || SmsClient.sendSms(requestDto).getResponseCode() == 200) {
            responseDto.setStatus(true);
            responseDto.setStatusDesc("SMS Gönderildi”");
            responseDto.setMessageID("1");
        } else {
            SmsClient.sendSms(requestDto).getResponseMessage();
            responseDto.setStatus(false);
            responseDto.setStatusDesc(SmsClient.sendSms(requestDto).getResponseMessage());
            responseDto.setMessageID(null);
        }
        Response response = new Response();
        response.setMessageID(responseDto.getMessageID());
        response.setStatus(responseDto.isStatus());
        response.setStatusDesc(responseDto.getStatusDesc());
        responseRepository.save(response);
        return responseDto;
    }
}

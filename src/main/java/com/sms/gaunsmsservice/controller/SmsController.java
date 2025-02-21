package com.sms.gaunsmsservice.controller;

import com.sms.gaunsmsservice.dto.RequestDto;
import com.sms.gaunsmsservice.dto.ResponseDto;
import com.sms.gaunsmsservice.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }
    @PostMapping("/smsApi")
    public ResponseEntity<ResponseDto> smsApi(@RequestBody RequestDto requestDto) throws IOException {
        return ResponseEntity.ok(smsService.sendSms(requestDto));
    }
}

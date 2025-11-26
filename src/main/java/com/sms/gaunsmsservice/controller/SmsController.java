package com.sms.gaunsmsservice.controller;

import com.sms.gaunsmsservice.dto.RequestDto;
import com.sms.gaunsmsservice.dto.ResponseDto;
import com.sms.gaunsmsservice.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
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

package com.sms.gaunsmsservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.gaunsmsservice.dto.RequestDto;
import com.sms.gaunsmsservice.dto.SmsApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class SmsClient {

    @Value("${sms.api.url}")
    private String apiUrl;
    
    @Value("${sms.api.id}")
    private String apiId;
    
    @Value("${sms.api.key}")
    private String apiKey;
    
    @Value("${sms.api.sender}")
    private String sender;
    
    @Value("${sms.api.message.type}")
    private String messageType;
    
    @Value("${sms.api.message.content.type}")
    private String messageContentType;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SmsApiResponse sendSms(RequestDto requestDto) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = buildRequestJson(requestDto);
            
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Parse response
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            }

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();

            String responseBody = response.toString();
            System.out.println("Response Body: " + responseBody);

            // Parse JSON response
            SmsApiResponse apiResponse = objectMapper.readValue(responseBody, SmsApiResponse.class);
            return apiResponse;

        } catch(Exception e) {
            e.printStackTrace();
            // Return error response
            SmsApiResponse errorResponse = new SmsApiResponse();
            errorResponse.setStatus("error");
            errorResponse.setDescription("SMS gönderimi sırasında hata oluştu: " + e.getMessage());
            errorResponse.setId(null);
            return errorResponse;
        }
    }

    private String buildRequestJson(RequestDto requestDto) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("api_id", apiId);
        params.put("api_key", apiKey);
        params.put("sender", sender);
        params.put("message_type", messageType);
        params.put("message", requestDto.getMsg());
        params.put("message_content_type", messageContentType);
        params.put("phones", requestDto.getGsm());

        return objectMapper.writeValueAsString(params);
    }
}

package com.sms.gaunsmsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.gaunsmsservice.dto.RequestDto;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SmsClient {

    public static Integer responseCode=null;
    public static String responseMessage=null;

    public static HttpURLConnection sendSms(RequestDto requestDto) {

        try {
            URL url = new URL("https://api.vatansms.net/api/v1/1toN");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = getString(requestDto);
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Response Message: " + conn.getResponseMessage());
            return  conn;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getString(RequestDto requestDto) throws JsonProcessingException {
        Map<String, Object> params = new HashMap<>();
        params.put("api_id", "29d463733f56db81be9eb355");
        params.put("api_key", "79259ea325e14e8603ab7cf7");
        params.put("sender", "G.ANTEP UNI");
        params.put("message_type", "normal");
        params.put("message", requestDto.getMsg());
        params.put("message_content_type", "bilgi");
        params.put("phones", requestDto.getGsm());

        ObjectMapper mapper = new ObjectMapper();
        String jsonInputString = mapper.writeValueAsString(params);
        return jsonInputString;
    }
}

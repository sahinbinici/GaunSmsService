package com.sms.gaunsmsservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsApiResponse {
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("numberCount")
    private Integer numberCount;
    
    @JsonProperty("amount")
    private Integer amount;
    
    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status);
    }
}

package com.sms.gaunsmsservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private String msg;
    private String source;
    private String[] gsm;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getGsm() {
        return gsm;
    }

    public void setGsm(String[] gsm) {
        this.gsm = gsm;
    }
}

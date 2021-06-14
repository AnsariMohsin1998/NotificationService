package com.meesho.mohsin.NotificationService.model.response;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ErrorMessageResponse {
    private String code;
    private String message;
    public ErrorMessageResponse(){
        code = "INVALID_REQUEST";
        message = "phone_number is mandatory";
    }
    public ErrorMessageResponse(String error){
        code = "INVALID_REQUEST";
        message = error;
    }
}

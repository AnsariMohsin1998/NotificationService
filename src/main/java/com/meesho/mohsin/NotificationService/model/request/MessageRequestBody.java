package com.meesho.mohsin.NotificationService.model.request;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageRequestBody {
    private String phone_number;
    private String message;
    MessageRequestBody(){}
}

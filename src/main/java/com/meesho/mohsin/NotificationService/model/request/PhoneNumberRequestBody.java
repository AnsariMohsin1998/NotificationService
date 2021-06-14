package com.meesho.mohsin.NotificationService.model.request;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@Component
public class PhoneNumberRequestBody {
    private ArrayList<String> phone_numbers;
    PhoneNumberRequestBody(){}
}

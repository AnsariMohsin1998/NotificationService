package com.meesho.mohsin.NotificationService.model.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
public class BlackListResponse {
    private Set<String> data;
}

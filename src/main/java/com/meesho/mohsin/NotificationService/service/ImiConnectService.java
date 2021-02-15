package com.meesho.mohsin.NotificationService.service;

import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.imiconnectmodel.ImiConnectSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImiConnectService {

    @Value("${imiconnect.url}")
    private String url;
    @Value("${imiconnect.api.key}")
    private String key;

    @Autowired
    RestTemplate restTemplate;

    public String send(SmsRequests sms) {
        ImiConnectSms imiConnectSms = new ImiConnectSms(sms);
        try {
            String response = restTemplate.postForObject(url,imiConnectSms,String.class);
            return response;
        }
        catch (Exception E){
            return String.valueOf(E);
        }
    }

}

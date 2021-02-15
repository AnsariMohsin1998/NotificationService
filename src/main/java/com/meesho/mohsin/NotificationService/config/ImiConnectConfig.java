package com.meesho.mohsin.NotificationService.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ImiConnectConfig {

    @Value("${imiconnect.url}")
    private String uri;

    @Value("${imiconnect.api.key}")
    private String key;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().rootUri(uri).defaultHeader("key",key).defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
    }

}

package com.meesho.mohsin.NotificationService.model.imiconnectmodel;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ImiConnectSms {
    @JsonProperty("deliverychannel")
    private String deliveryChannel;

    @JsonProperty("channels")
    private Channels channels;

    @JsonProperty("destination")
    List<Destination> destination;

    public ImiConnectSms(SmsRequests smsRequest){
        this.deliveryChannel = "sms";
        this.channels = new Channels(new Sms(smsRequest.getMessage()));
        Destination destination = Destination.builder().correlationid(String.valueOf(smsRequest.getId())).msisdn(Arrays.asList(smsRequest.getPhone_number())).build();
        this.destination = Arrays.asList(destination);
    }

}

package com.meesho.mohsin.NotificationService.model.imiconnectmodel;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Channels {

    @JsonProperty("sms")
    private Sms sms;

}

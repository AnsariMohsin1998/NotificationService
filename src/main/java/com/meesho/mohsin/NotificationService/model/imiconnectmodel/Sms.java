package com.meesho.mohsin.NotificationService.model.imiconnectmodel;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {

    @JsonProperty("text")
    private String text;
}

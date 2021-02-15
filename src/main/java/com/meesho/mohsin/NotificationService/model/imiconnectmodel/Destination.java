package com.meesho.mohsin.NotificationService.model.imiconnectmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Destination {

    private List<String> msisdn;

    private String correlationid;

}

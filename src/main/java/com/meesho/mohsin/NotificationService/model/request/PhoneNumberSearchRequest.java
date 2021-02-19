package com.meesho.mohsin.NotificationService.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberSearchRequest {

// dd/MM/yyyy HH:mm:ss{
//        "startDate":"01/09/2020 00:00:00",
//            "endDate":"09/02/2021 11:00:00"
//    }

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("offset")
    private Integer offset = 0;

    @JsonProperty("limit")
    private Integer limit = 50;

}
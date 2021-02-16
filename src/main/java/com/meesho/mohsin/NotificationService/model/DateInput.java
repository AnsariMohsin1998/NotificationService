package com.meesho.mohsin.NotificationService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateInput {

// dd/MM/yyyy HH:mm:ss{
//        "startDate":"01/09/2020 00:00:00",
//            "endDate":"09/02/2021 11:00:00"
//    }
    private String startDate;
    private String endDate;

}
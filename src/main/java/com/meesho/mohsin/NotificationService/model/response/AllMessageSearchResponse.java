package com.meesho.mohsin.NotificationService.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.SmsRequestDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllMessageSearchResponse {

    @JsonProperty("sms_list")
    List<SmsRequestDocument> elasticSearchBodiesList;

}

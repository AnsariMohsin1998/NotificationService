package com.meesho.mohsin.NotificationService.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meesho.mohsin.NotificationService.dto.ElasticSearchDto;
import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.SmsRequestDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class MessageSearchResponse {

    @JsonProperty("sms_list")
    List<ElasticSearchDto> elasticSearchBodiesList;

}

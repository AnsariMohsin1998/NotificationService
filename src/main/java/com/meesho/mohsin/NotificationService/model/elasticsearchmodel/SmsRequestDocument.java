package com.meesho.mohsin.NotificationService.model.elasticsearchmodel;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

@Builder
@Document(indexName = "sms_details")
@Data
public class SmsRequestDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text,name = "phone_number")
    private String phoneNumber;

    @Field(type =  FieldType.Text)
    @JsonProperty("message")
    private String message;

    @JsonProperty("created_at")
    @Field(type = FieldType.Date)
    private Date createdAt;


}

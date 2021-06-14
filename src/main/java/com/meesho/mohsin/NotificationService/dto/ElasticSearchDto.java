package com.meesho.mohsin.NotificationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class ElasticSearchDto implements Serializable {

    private String id;

    private String phoneNumber;

    private String message;

    private Date createdAt;
}

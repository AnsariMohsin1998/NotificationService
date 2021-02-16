package com.meesho.mohsin.NotificationService.model.elasticsearchmodel;


import lombok.Builder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

@Builder
@Document(indexName = "sms_details")
public class ElasticSearchBody {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String phone_number;

    @Field(type =  FieldType.Text)
    private String message;

    @Field(type = FieldType.Date)
    private Date createdAt;

    ElasticSearchBody(){
    }

    public ElasticSearchBody(String id, String phone_number, String message, Date createdAt) {
        this.id = id;
        this.phone_number = phone_number;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}

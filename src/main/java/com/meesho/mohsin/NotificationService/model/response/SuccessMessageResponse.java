package com.meesho.mohsin.NotificationService.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

@Data
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessageResponse {
    private String request_id;

    private String phone_number;

    private String message;

    private String status;

    private String failure_code;

    private String comments;

    private Date created_at;

    private Date updated_at;

    public SuccessMessageResponse(){}

    public void setDetails(SmsRequests sms) {
        this.request_id = String.valueOf(sms.getId());
        this.phone_number=sms.getPhone_number();
        this.message = sms.getMessage();
        this.failure_code = sms.getFailure_code();
        this.comments = sms.getFailure_comments();
        this.created_at = sms.getCreated_at();
        this.updated_at = sms.getUpdated_at();
    }
}


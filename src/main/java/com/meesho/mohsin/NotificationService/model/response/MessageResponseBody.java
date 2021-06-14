package com.meesho.mohsin.NotificationService.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseBody {

    public ErrorMessageResponse error;
    public SuccessMessageResponse data;

    public MessageResponseBody(){}
    public MessageResponseBody(ErrorMessageResponse error) {
        this.error = error;
    }
    public MessageResponseBody(SuccessMessageResponse data) {
        this.data = data;
    }

    public ErrorMessageResponse getError() {
        return error;
    }

    public void setError(ErrorMessageResponse error) {
        this.error = error;
    }

    public SuccessMessageResponse getData() {
        return data;
    }

    public void setData(SuccessMessageResponse data) {
        this.data = data;
    }
}

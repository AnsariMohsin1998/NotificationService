package com.meesho.mohsin.NotificationService.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class PhoneNumberSearchResponse {

    @JsonProperty("phone_numbers")
    Set<String> phoneNumbers;

    @JsonProperty("has_next")
    Boolean hasNext;

}

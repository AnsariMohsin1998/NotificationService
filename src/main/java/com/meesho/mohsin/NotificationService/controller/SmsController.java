package com.meesho.mohsin.NotificationService.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.request.MessageRequestBody;
import com.meesho.mohsin.NotificationService.model.request.PhoneNumberRequestBody;
import com.meesho.mohsin.NotificationService.model.response.ErrorMessageResponse;
import com.meesho.mohsin.NotificationService.model.response.MessageResponseBody;
import com.meesho.mohsin.NotificationService.model.response.SuccessMessageResponse;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import com.meesho.mohsin.NotificationService.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class SmsController {

    // LOGGER

    private static final Logger log = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    public SmsRepository smsRepository;

    @GetMapping(value = "/sms/get/bulk")
    public List<SmsDto> getAllSms(){
        return smsService.findAllSmsRequests();
    }

    @GetMapping(value = "/sms/get/{id}")
    public SmsDto findSmsById(@PathVariable(value = "id") int id) throws JsonProcessingException {
        return smsService.findSmsRequestById(id);
    }

    @PostMapping(value = "/sms/send")
    public ResponseEntity<MessageResponseBody> send(@RequestBody MessageRequestBody messageRequestBody){
        try{
            //kafkaController.sendToKafka(String.valueOf(sms.getId()));
            SuccessMessageResponse successMessageResponse = smsService.sendMessage(messageRequestBody);
            return new ResponseEntity<>(new MessageResponseBody(successMessageResponse), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(new MessageResponseBody(new ErrorMessageResponse()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/blacklist")
    public ResponseEntity<MessageResponseBody> addToBlacklist(@RequestBody PhoneNumberRequestBody phoneNumberRequestBody) {
        try{
            SuccessMessageResponse successMessageResponse = smsService.addToBlackList(phoneNumberRequestBody);
            return new ResponseEntity(new MessageResponseBody(successMessageResponse),HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity(new MessageResponseBody(new ErrorMessageResponse("coudn't add to blacklist")),HttpStatus.NOT_FOUND);
        }
    }

}
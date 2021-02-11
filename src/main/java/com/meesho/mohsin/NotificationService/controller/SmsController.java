package com.meesho.mohsin.NotificationService.controller;


import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.request.MessageRequestBody;
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
@RequestMapping("/v1/sms")
public class SmsController {

    // LOGGER

    private static final Logger log = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    public SmsRepository smsRepository;

    @GetMapping(value = "/get/bulk")
    public List<SmsDto> getAllSms(){
        log.info("getAllSms() called inside SmsController");
        return smsService.findAllSmsRequests();
    }

    @GetMapping(value = "/get/{id}")
    public SmsDto findSmsById(@PathVariable(value = "id") int id){
        log.info("findSmsById() called inside SmsController");
        return smsService.findSmsRequestById(id);
    }

    @PostMapping(value = "/send")
    public ResponseEntity<MessageResponseBody> send(@RequestBody MessageRequestBody messageRequestBody){
        try{
            SmsRequests sms = new SmsRequests();
            sms.setPhone_number(messageRequestBody.getPhone_number());
            sms.setMessage(messageRequestBody.getMessage());
            sms.setStatus("OK");
            smsRepository.save(sms);

            //kafkaController.sendToKafka(String.valueOf(sms.getId()));
            SuccessMessageResponse successMessageResponse = new SuccessMessageResponse();
            successMessageResponse.setRequest_id(String.valueOf(sms.getId()));
            successMessageResponse.setComments("Successfully Sent");

            return new ResponseEntity<>(new MessageResponseBody(successMessageResponse), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(new MessageResponseBody(new ErrorMessageResponse()), HttpStatus.NOT_FOUND);
        }
    }


}
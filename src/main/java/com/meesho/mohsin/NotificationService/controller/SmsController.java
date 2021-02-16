package com.meesho.mohsin.NotificationService.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.DateInput;
import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.ElasticSearchBody;
import com.meesho.mohsin.NotificationService.model.request.MessageRequestBody;
import com.meesho.mohsin.NotificationService.model.request.PhoneNumberRequestBody;
import com.meesho.mohsin.NotificationService.model.response.BlackListResponse;
import com.meesho.mohsin.NotificationService.model.response.ErrorMessageResponse;
import com.meesho.mohsin.NotificationService.model.response.MessageResponseBody;
import com.meesho.mohsin.NotificationService.model.response.SuccessMessageResponse;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import com.meesho.mohsin.NotificationService.producers.KafkaProducerService;
import com.meesho.mohsin.NotificationService.service.ELasticSearchService;
import com.meesho.mohsin.NotificationService.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    public KafkaProducerService kafkaProducerService;

    @Autowired
    ELasticSearchService eLasticSearchService;

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
            SuccessMessageResponse successMessageResponse = smsService.addToBlackList(phoneNumberRequestBody.getPhone_numbers());
            return new ResponseEntity(new MessageResponseBody(successMessageResponse),HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity(new MessageResponseBody(new ErrorMessageResponse("coudn't add to blacklist")),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/blacklist/delete/{phone_number}")
    public ResponseEntity<MessageResponseBody> delete(@PathVariable(value = "phone_number") String phone_number){
        try {
            SuccessMessageResponse successMessageResponse =  smsService.deleteFromBlackList(phone_number);
            return new ResponseEntity(new MessageResponseBody(successMessageResponse),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(new MessageResponseBody(new ErrorMessageResponse("coudn't delete from blacklist")),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/blacklist")
    public ResponseEntity<BlackListResponse> getBlacklist(){
        BlackListResponse blackListResponse = smsService.getBlackList();

        return new ResponseEntity(blackListResponse, HttpStatus.OK);
    }

    @GetMapping ("/searchSmsContainingText/{text}")
    public List<ElasticSearchBody> getAllSmsConstainingText(@PathVariable String text){
        log.info("INSIDE getAllSmsConstainingText(SmsController)");
        return eLasticSearchService.getAllSmsConstainingText(text);
    }

    @GetMapping("/searchMobileNumbersBetweenDate")
    public Page<ElasticSearchBody> getAllBetweenDate(@RequestBody DateInput dateInput){
        log.info("Inside getAllBetweenDate");
        return eLasticSearchService.getAllSmsBetweenStartAndEndTime(dateInput);
    }
}
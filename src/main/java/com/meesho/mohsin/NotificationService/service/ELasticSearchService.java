package com.meesho.mohsin.NotificationService.service;


import com.meesho.mohsin.NotificationService.model.request.PhoneNumberSearchRequest;
import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.SmsRequestDocument;
import com.meesho.mohsin.NotificationService.model.response.AllMessageSearchResponse;
import com.meesho.mohsin.NotificationService.model.response.MessageSearchResponse;
import com.meesho.mohsin.NotificationService.model.response.PhoneNumberSearchResponse;
import com.meesho.mohsin.NotificationService.repository.SearchRepository;
import com.meesho.mohsin.NotificationService.util.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ELasticSearchService {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    Converters converters;

    private static final Logger log = LoggerFactory.getLogger(ELasticSearchService.class);

    public MessageSearchResponse getAllSmsConstainingText(String text) {

        log.info("INSIDE getAllSmsConstainingText(ElasticSearchService)");

        // TAKE OFFSET AND LIMIT FROM REQUEST
        // MAKE ELASTIC SEARCH DTO

        Page<SmsRequestDocument> elasticSearchBodies = searchRepository.findByMessage(text, PageRequest.of(0,5));
        List<SmsRequestDocument> elasticSearchBodiesList = elasticSearchBodies.getContent();

        return new MessageSearchResponse(elasticSearchBodiesList);
    }

    public PhoneNumberSearchResponse searchPhoneNumbers(PhoneNumberSearchRequest phoneNumberSearchRequest) {

        log.info("Inside getAllSmsBetweenStartAndEndTime");
        long startEpoch = converters.DateConverter(phoneNumberSearchRequest.getStartDate());
        long endEpoch = converters.DateConverter(phoneNumberSearchRequest.getEndDate());

        Page<SmsRequestDocument> elasticSearchBodyPage = searchRepository.findAllByCreatedAtBetween(startEpoch,endEpoch,PageRequest.of(phoneNumberSearchRequest.getOffset(),phoneNumberSearchRequest.getLimit()));
        Set<String> phoneNumbers = new HashSet<>();
        for (SmsRequestDocument smsRequestDocument : elasticSearchBodyPage.getContent()) {
            phoneNumbers.add(smsRequestDocument.getPhoneNumber());
        }
        boolean hasNext = elasticSearchBodyPage.getTotalPages()>elasticSearchBodyPage.getPageable().getPageSize();
        return new PhoneNumberSearchResponse(phoneNumbers,hasNext);
    }

    public AllMessageSearchResponse getAll() {
        Page<SmsRequestDocument> allMessages = searchRepository.findAll();
        List<SmsRequestDocument> smsRequestDocumentList = allMessages.getContent();

        return new AllMessageSearchResponse(smsRequestDocumentList);
    }
}

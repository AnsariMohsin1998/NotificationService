package com.meesho.mohsin.NotificationService.service;


import com.meesho.mohsin.NotificationService.model.DateInput;
import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.ElasticSearchBody;
import com.meesho.mohsin.NotificationService.repository.SearchRepository;
import com.meesho.mohsin.NotificationService.util.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ELasticSearchService {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    Converters converters;

    private static final Logger log = LoggerFactory.getLogger(ELasticSearchService.class);

    public List<ElasticSearchBody> getAllSmsConstainingText(String text) {

        log.info("INSIDE getAllSmsConstainingText(ElasticSearchService)");

        Page<ElasticSearchBody> elasticSearchBodies = searchRepository.findByMessage(text, PageRequest.of(0,15));
        List<ElasticSearchBody> elasticSearchBodiesList = elasticSearchBodies.getContent();
        return elasticSearchBodiesList;
    }

    public Page<ElasticSearchBody> getAllSmsBetweenStartAndEndTime(DateInput dateInput) {

        log.info("Inside getAllSmsBetweenStartAndEndTime");
        long startEpoch = converters.DateConverter(dateInput.getStartDate());
        long endEpoch = converters.DateConverter(dateInput.getEndDate());

        return searchRepository.findAllByCreatedAtBetween(startEpoch,endEpoch,PageRequest.of(0,15));
    }
}

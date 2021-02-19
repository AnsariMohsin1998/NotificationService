package com.meesho.mohsin.NotificationService.repository;

import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.SmsRequestDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchRepository extends ElasticsearchRepository<SmsRequestDocument,Integer> {

    Page<SmsRequestDocument> findByMessage(String text, Pageable pageable);
    Page<SmsRequestDocument> findAllByCreatedAtBetween(long startEpoch, long endEpoch, Pageable pageable);
    Page<SmsRequestDocument> findAll();
}


// Design Pattern -> Builder pattern
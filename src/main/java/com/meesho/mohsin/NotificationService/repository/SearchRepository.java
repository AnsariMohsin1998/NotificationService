package com.meesho.mohsin.NotificationService.repository;

import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.ElasticSearchBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchRepository extends ElasticsearchRepository<ElasticSearchBody,Integer> {

    Page<ElasticSearchBody> findByMessage(String text, Pageable pageable);
    Page<ElasticSearchBody> findAllByCreatedAtBetween(long startEpoch,long endEpoch,Pageable pageable);

}


// Design Pattern -> Builder pattern
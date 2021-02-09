package com.meesho.mohsin.NotificationService.repository;

import com.meesho.mohsin.NotificationService.model.SmsRequests;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SmsRepository extends CrudRepository<SmsRequests,Long> {

    List<SmsRequests> findAll();
    SmsRequests findById(int id);  // why can't i use Long ?

}

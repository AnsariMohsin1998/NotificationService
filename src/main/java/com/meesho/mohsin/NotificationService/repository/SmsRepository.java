package com.meesho.mohsin.NotificationService.repository;

import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.response.SuccessMessageResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsRepository extends JpaRepository<SmsRequests, Integer> {

    List<SmsRequests> findAll();
    SmsRequests findById(int id);

}

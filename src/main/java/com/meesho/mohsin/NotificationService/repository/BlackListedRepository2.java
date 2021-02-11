package com.meesho.mohsin.NotificationService.repository;

import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlackListedRepository2 extends JpaRepository<BlackListedEntity,String> {
    List<BlackListedEntity> findAll();
}

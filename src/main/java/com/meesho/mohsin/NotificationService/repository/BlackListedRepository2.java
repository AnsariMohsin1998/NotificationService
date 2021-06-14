package com.meesho.mohsin.NotificationService.repository;

import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlackListedRepository2 extends JpaRepository<BlackListedEntity,String> {
}

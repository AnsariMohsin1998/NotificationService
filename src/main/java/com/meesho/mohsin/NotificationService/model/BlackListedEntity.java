package com.meesho.mohsin.NotificationService.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "blacklisted_phone_nos")
public class BlackListedEntity {

    @Id
    @Column(nullable = false)
    public String phone_number;

}

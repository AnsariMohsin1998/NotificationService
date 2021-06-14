package com.meesho.mohsin.NotificationService.model;


import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "sms_requests")
public class SmsRequests {

    //  FIELDS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String phone_number;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String status;

    private String failure_code;

    private String failure_comments;

    @Column(nullable = false)
    private Date created_at = new Date();

    private Date updated_at;

    @PreUpdate
    public void preUpdate(){
        updated_at = new Date();
    }


    // NO NEED FOR GETTERS AND SETTERS AND CONSTRUCTORS, AS DATA ANNOTATION WILL HANDLE IT.


    //   TO STRING

    @Override
    public String toString() {
        return "SmsRequests{" +
                "id=" + id +
                ", phone_number='" + phone_number + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", failure_code='" + failure_code + '\'' +
                ", failure_comments='" + failure_comments + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}

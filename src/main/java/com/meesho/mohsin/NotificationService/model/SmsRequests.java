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
    private int id;

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


    //  CONSTRUCTORS


    public SmsRequests(int id, String phone_number, String message, String status, String failure_code, String failure_comments, Date created_at, Date updated_at) {
        this.id = id;
        this.phone_number = phone_number;
        this.message = message;
        this.status = status;
        this.failure_code = failure_code;
        this.failure_comments = failure_comments;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public SmsRequests() {

    }

    //  GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(String failure_code) {
        this.failure_code = failure_code;
    }

    public String getFailure_comments() {
        return failure_comments;
    }

    public void setFailure_comments(String failure_comments) {
        this.failure_comments = failure_comments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }


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

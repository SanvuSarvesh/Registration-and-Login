package com.example.authenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private String firstName;

    private String lastName;

    private String mobileNo;

    private String emailId;

    private String password;

    private Date dateOfBirth;

    private boolean isActive;

    private String otp;

    private LocalDateTime otpGeneratedAt;

    private Date accountCreatedOn;

    //private List<Followers> followersList;


}

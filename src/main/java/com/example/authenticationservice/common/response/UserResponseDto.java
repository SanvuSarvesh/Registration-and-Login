package com.example.authenticationservice.common.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDto {

    private String userName;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

}

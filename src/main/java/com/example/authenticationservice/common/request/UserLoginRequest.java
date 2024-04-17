package com.example.authenticationservice.common.request;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String emailId;

    private String password;

}

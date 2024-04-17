package com.example.authenticationservice.common.request;

import com.example.authenticationservice.models.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequestDto extends UserInfo {

    private String userName;

    private String firstName;

    private String lastName;

    private String mobileNo;

    private String emailId;

    private String password;

    private Date dateOfBirth;

}

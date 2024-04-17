package com.example.authenticationservice.dtoTransfer;

import com.example.authenticationservice.common.request.UserRequestDto;
import com.example.authenticationservice.models.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDtoToUserInfo {

    @Autowired
    private ObjectMapper objectMapper;

    public UserInfo userDtoToUserInfoConversion(UserRequestDto userRequestDto){
         return objectMapper.convertValue(userRequestDto,UserInfo.class);
    }
}
package com.example.authenticationservice.services;

import com.example.authenticationservice.common.request.UserLoginRequest;
import com.example.authenticationservice.common.request.UserRequestDto;
import com.example.authenticationservice.common.response.BaseResponse;

public interface UserService {

    BaseResponse UserRegistration(UserRequestDto userRequestDto);

    BaseResponse verifyAccount(String emailId, String otp);

    BaseResponse regenerateOtp(String emailId);

    BaseResponse userLogin(UserLoginRequest UserLoginRequest);

}

package com.example.authenticationservice.services.impl;

import com.example.authenticationservice.common.request.UserLoginRequest;
import com.example.authenticationservice.common.request.UserRequestDto;
import com.example.authenticationservice.common.response.BaseResponse;
import com.example.authenticationservice.exceptions.UserServiceException;
import com.example.authenticationservice.models.UserInfo;
import com.example.authenticationservice.repositories.UserRepository;
import com.example.authenticationservice.services.UserService;
import com.example.authenticationservice.utils.EmailUtils;
import com.example.authenticationservice.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.authenticationservice.common.constant.ExceptionMessage.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userDetailsRepository;

    @Autowired
    private OtpUtils otpUtils;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public BaseResponse UserRegistration(UserRequestDto userRequestDto) {
        BaseResponse baseResponse = new BaseResponse();
        String otp = otpUtils.generateOtp();
        try{
            String emailId = userRequestDto.getEmailId();
            emailUtils.sendOtpToMail(emailId,otp);
        }catch (Exception exception){
            throw new UserServiceException(UNABLE_TO_SEND_OTP,HttpStatus.BAD_REQUEST);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userRequestDto.getUserName());
        userInfo.setFirstName(userRequestDto.getFirstName());
        userInfo.setLastName(userRequestDto.getLastName());
        userInfo.setMobileNo(userRequestDto.getMobileNo());
        userInfo.setDateOfBirth(userRequestDto.getDateOfBirth());
        userInfo.setEmailId(userRequestDto.getEmailId());
        userInfo.setPassword(userRequestDto.getPassword());
        userInfo.setOtp(otp);
        userInfo.setOtpGeneratedAt(LocalDateTime.now());

        userDetailsRepository.save(userRequestDto);

        baseResponse.setPayload(userInfo);
        baseResponse.setSuccess(true);
        baseResponse.setStatusCode(String.valueOf(HttpStatus.OK));
        baseResponse.setMessage("Registration Successful.");
        return  baseResponse;
    }


    @Override
    public BaseResponse verifyAccount(String emailId, String otp) {
        BaseResponse baseResponse = new BaseResponse();
        Optional<UserInfo> userInfoOptional = userDetailsRepository.findByEmailId(emailId);
        if(userInfoOptional.isEmpty()){
            throw new UserServiceException(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
        UserInfo userInfo = userInfoOptional.get();
        if(userInfo.getOtp().equals(otp) && Duration.between(userInfo.getOtpGeneratedAt(),
                LocalDateTime.now()).getSeconds() < (2 * 60)){
            userInfo.setActive(true);
            userDetailsRepository.save(userInfo);
            baseResponse.setMessage("Account verification successful.");
            baseResponse.setSuccess(true);
            baseResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            return baseResponse;
        }
        userInfo.setActive(false);
        baseResponse.setMessage(ACCOUNT_NOT_VERIFIED);
        baseResponse.setSuccess(false);
        baseResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
        return baseResponse;
    }

    @Override
    public BaseResponse regenerateOtp(String emailId) {
        BaseResponse baseResponse = new BaseResponse();
        Optional<UserInfo> userInfoOptional = userDetailsRepository.findByEmailId(emailId);
        if(userInfoOptional.isEmpty()){
            throw new UserServiceException(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
        UserInfo userInfo = userInfoOptional.get();
        String otp = otpUtils.generateOtp();
        try{
            emailUtils.sendOtpToMail(emailId,otp);
        }catch (Exception exception){
            throw new UserServiceException(UNABLE_TO_SEND_OTP,HttpStatus.BAD_REQUEST);
        }
        userInfo.setOtp(otp);
        userInfo.setOtpGeneratedAt(LocalDateTime.now());
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Otp has been sent to your registered email, please verify.");
        baseResponse.setStatusCode(String.valueOf(HttpStatus.OK));
        return baseResponse;
    }

    @Override
    public BaseResponse userLogin(UserLoginRequest userLoginRequest ) {
        BaseResponse baseResponse = new BaseResponse();
        String emailId = userLoginRequest.getEmailId();
        Optional<UserInfo> userInfoOptional = userDetailsRepository.findByEmailId(emailId);
        if(userInfoOptional.isEmpty()){
            throw new UserServiceException(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
        UserInfo userInfo = userInfoOptional.get();
        if(userInfo.getPassword().equals(userLoginRequest.getPassword()) && !userInfo.isActive()){
            baseResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseResponse.setMessage("Login failed, you need to verify your account first...");
            baseResponse.setSuccess(false);
        }
        if(!userInfo.getPassword().equals(userLoginRequest.getPassword()) ){
            baseResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseResponse.setMessage(PASSWORD_MISMATCHED);
            baseResponse.setSuccess(false);
        }
        if(userInfo.getPassword().equals(userLoginRequest.getPassword()) && userInfo.isActive()){
            baseResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseResponse.setMessage("Login successful.");
            baseResponse.setSuccess(true);
        }
        baseResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
        baseResponse.setMessage("Login failed.");
        baseResponse.setSuccess(false);
        return baseResponse;
    }
}
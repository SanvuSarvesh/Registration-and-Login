package com.example.authenticationservice.controllers;

import com.example.authenticationservice.common.request.UserLoginRequest;
import com.example.authenticationservice.common.request.UserRequestDto;
import com.example.authenticationservice.common.response.BaseResponse;
import com.example.authenticationservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String TIME_TAKEN_BY = "Time taken by this api is : " ;
    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerNewUser(@RequestBody UserRequestDto userRequestDto){
        logger.info("Inside the registerNewUser");
        long startTime = System.currentTimeMillis();
        BaseResponse baseResponse = userService.UserRegistration(userRequestDto);
        logger.info(TIME_TAKEN_BY + "registerNewUser "+ (System.currentTimeMillis() - startTime));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<BaseResponse>  verifyAccount(@RequestParam("email") String emailId,@RequestParam("otp") String otp){
        logger.info("Inside the verifyAccount");
        long startTime = System.currentTimeMillis();
        BaseResponse baseResponse = userService.verifyAccount(emailId,otp);
        logger.info(TIME_TAKEN_BY + "verifyAccount "+ (System.currentTimeMillis() - startTime));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/regenerate-otp")
    public ResponseEntity<?> regenerateOtp(@RequestParam("email") String emailId){
        logger.info("Inside the regenerateOtp");
        long startTime = System.currentTimeMillis();
        BaseResponse baseResponse = userService.regenerateOtp(emailId);
        logger.info("Time taken by this api is : "+"regenerateOtp "+ (System.currentTimeMillis() - startTime));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        logger.info("Inside the userLogin");
        long startTime = System.currentTimeMillis();
        BaseResponse baseResponse = userService.userLogin(userLoginRequest);
        logger.info(TIME_TAKEN_BY+ " userLogin"+ (System.currentTimeMillis() - startTime));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

}

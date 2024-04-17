package com.example.authenticationservice.controllers;

import com.example.authenticationservice.common.request.UserLoginRequest;
import com.example.authenticationservice.common.request.UserRequestDto;
import com.example.authenticationservice.common.response.BaseResponse;
import com.example.authenticationservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerNewUser(@RequestBody UserRequestDto userRequestDto){
        BaseResponse baseResponse = userService.UserRegistration(userRequestDto);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<BaseResponse>  verifyAccount(@RequestParam("email") String emailId,@RequestParam("otp") String otp){
        BaseResponse baseResponse = userService.verifyAccount(emailId,otp);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/regenerate-otp")
    public ResponseEntity<?> regenerateOtp(@RequestParam("email") String emailId){
        BaseResponse baseResponse = userService.regenerateOtp(emailId);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        BaseResponse baseResponse = userService.userLogin(userLoginRequest);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

}

package com.example.authenticationservice.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtils {


    public String generateOtp(){
        Random random = new Random();
        int randomNUmber = random.nextInt(999999);
        String finalOtp = Integer.toString(randomNUmber);
        while(finalOtp.length() < 6){
            finalOtp = "0" + finalOtp;
        }
        return "";
    }
}

package com.example.authenticationservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class UserServiceException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public UserServiceException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

    public UserServiceException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

}

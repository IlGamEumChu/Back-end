package com.ilgamumchu.demar.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationEntryPointException extends RuntimeException {
    public AuthenticationEntryPointException() {
        super("AuthenticationEntryPointException");
    }
}
package com.clock.clockapi.security.exception;

import com.clock.clockapi.security.model.AuthenticationJwtRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
public class BadCredentialsException extends Exception {

    private String message;
    private AuthenticationJwtRequest authenticationJwtRequest;

    public BadCredentialsException(String message, AuthenticationJwtRequest authenticationJwtRequest) {
        this.message = message;
        this.authenticationJwtRequest = authenticationJwtRequest;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public AuthenticationJwtRequest getAuthenticationJwtRequest() {
        return authenticationJwtRequest;
    }
}

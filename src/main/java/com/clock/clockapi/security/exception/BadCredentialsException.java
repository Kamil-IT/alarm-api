package com.clock.clockapi.security.exception;

import com.clock.clockapi.security.model.AuthenticationJwtRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BadCredentialsException extends Exception {

    private String massage;
    private AuthenticationJwtRequest authenticationJwtRequest;
}

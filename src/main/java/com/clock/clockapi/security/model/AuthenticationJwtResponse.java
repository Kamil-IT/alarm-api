package com.clock.clockapi.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationJwtResponse {

    private final String jwt;
}

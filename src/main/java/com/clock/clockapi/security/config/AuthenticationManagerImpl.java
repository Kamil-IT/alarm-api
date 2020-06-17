package com.clock.clockapi.security.config;

import com.clock.clockapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = userService.getUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new CredentialsExpiredException(
                    String.format("Password: %s dont match to user with username: %s", password, username));
        }

        authentication.setAuthenticated(true);

        return authentication;
    }
}

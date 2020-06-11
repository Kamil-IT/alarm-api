package com.clock.clockapi.controller;

import com.clock.clockapi.security.JwtUtil;
import com.clock.clockapi.security.exception.BadCredentialsException;
import com.clock.clockapi.security.mapper.UserAppMapper;
import com.clock.clockapi.security.model.*;
import com.clock.clockapi.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserServiceImpl userService;
    private final UserAppMapper userAppMapper;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, UserServiceImpl userService, UserAppMapper userAppMapper, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userAppMapper = userAppMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping({"/auth", "/auth/"})
    public ResponseEntity<?> createJwtToken(@RequestBody AuthenticationJwtRequest request) throws BadCredentialsException {
//        TODO: Repair it
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//        }
//        catch (AuthenticationException e){
//            throw new BadCredentialsException("Incorrect password or username", request);
//        }
        final UserDetails userDetails;
        try {
            userDetails = userService
                    .loadUserByUsername(request.getUsername());
            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
                throw new RuntimeException();
            }
            log.error("I recognise you");
        }
        catch (Exception e){
            throw new BadCredentialsException("Incorrect password or username", request);
        }

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationJwtResponse(jwt));
    }

    @PostMapping({"/newaccount", "/newaccount/"})
    public UserApp createNewAccount(@RequestBody UserAppDto userDtoToCreate) throws BadCredentialsException {
        try {
            UserApp userApp = userAppMapper.UserAppDtoToUserApp(userDtoToCreate);
            return userService.saveUser(userApp);
        }
        catch (Exception e){
            throw new BadCredentialsException("Username or password is incorrect witch ours norms",
                    new AuthenticationJwtRequest(userDtoToCreate.getUsername(), userDtoToCreate.getPassword()));
        }
    }

    @DeleteMapping({"/deleteaccount", "/deleteaccount/"})
    public UserApp deleteAccount(@RequestBody UserAppDto userDtoToCreate) throws BadCredentialsException {
//        TODO: have to delete by username and check is password correct
        throw new NotYetImplementedException();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public BadCredentialsResponse handleBadCredentialsException(BadCredentialsException e){
        log.warn("Handled BadCredentialsException");
        return new BadCredentialsResponse(
                e.getMessage(),
                e.getAuthenticationJwtRequest());
    }
}

package com.clock.clockapi.controller;

import com.clock.clockapi.security.JwtUtil;
import com.clock.clockapi.security.exception.BadCredentialsException;
import com.clock.clockapi.security.mapper.UserAppMapper;
import com.clock.clockapi.security.model.*;
import com.clock.clockapi.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserServiceImpl userService;
    private final UserAppMapper userAppMapper;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil,
                                    UserServiceImpl userService, UserAppMapper userAppMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userAppMapper = userAppMapper;
    }


    /**
     * Paths: .../auth , .../auth/
     * Example:
     * page
     * page/
     *
     * Body:
     * {"username" : "admin", "password": "admin"}
     *
     * @return should return all jwt token
     */
    @PostMapping({"/auth", "/auth/"})
    public ResponseEntity<?> createJwtToken(@RequestBody AuthenticationJwtRequest request) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (AuthenticationException e){
            throw new BadCredentialsException(e.getMessage(), request);
        }

        UserDetails userDetails = userService
                .loadUserByUsername(request.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationJwtResponse(jwt));
    }

    /**
     * Paths: .../newaccount , .../newaccount/
     * Example:
     * page
     * page/
     *
     * Body:
     * {"username" : "admin", "password": "admin", "email" : "user@example.com"}
     *
     * @return should return created user
     */
    @PostMapping({"/newaccount", "/newaccount/"})
    public UserApp createNewAccount(@RequestBody UserAppDto userDtoToCreate) throws BadCredentialsException {
        try {
            UserApp userApp = userAppMapper.UserAppDtoToUserApp(userDtoToCreate);
            userApp.setAccountWorkingInServer();
            return userService.saveUser(userApp);
        }
        catch (Exception e){
            throw new BadCredentialsException("Username or password is incorrect witch ours norms or some errors while validation",
                    new AuthenticationJwtRequest(userDtoToCreate.getUsername(), userDtoToCreate.getPassword()));
        }
    }


    /**
     * Paths: .../deleteaccount , .../deleteaccount/
     * Example:
     * page
     * page/
     *
     * Body:
     * {"username" : "admin", "password": "admin"}
     *
     * @return should return message and deleted account
     */
    @DeleteMapping({"/deleteaccount", "/deleteaccount/"})
    public UserAppDeletedResponse deleteAccount(@RequestBody AuthenticationJwtRequest userDtoToDelete,
                                                Principal principal) throws BadCredentialsException {
        if (!principal.getName().equals(userDtoToDelete.getUsername())){
            throw new BadCredentialsException("Username isn't the same as users what you wont delete",
                    userDtoToDelete);
        }

        UserApp userToDelete;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDtoToDelete.getUsername(), userDtoToDelete.getPassword())
            );

            userToDelete = userService.getUserByUsername(userDtoToDelete.getUsername());

            userService.deleteUserById(userToDelete.getId());

        }
        catch (Exception e){
            throw new BadCredentialsException(
                    "User dont exists in db",
                    userDtoToDelete);
        }

        return new UserAppDeletedResponse(
                "Deleted was sucessfull",
                userToDelete);
    }

    /**
     * BadCredentialsException handler
     *
     * @return message and credentials
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BadCredentialsException.class)
    public BadCredentialsResponse handleBadCredentialsException(BadCredentialsException e){
        log.warn("Handled BadCredentialsException");
        return new BadCredentialsResponse(
                e.getMessage(),
                e.getAuthenticationJwtRequest());
    }
}

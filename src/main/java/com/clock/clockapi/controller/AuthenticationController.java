package com.clock.clockapi.controller;

import com.clock.clockapi.security.JwtUtil;
import com.clock.clockapi.security.exception.BadCredentialsException;
import com.clock.clockapi.security.mapper.UserAppMapper;
import com.clock.clockapi.security.model.*;
import com.clock.clockapi.services.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

import static com.clock.clockapi.swagger.SpringFoxConfig.JWT_TOKEN_NAME_SWAGGER;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "Authentication manager")
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
    @ApiOperation(value="Post create JWT token")
    @PostMapping({"/auth", "/auth/"})
    public ResponseEntity<?> createJwtToken(@ApiParam(name = "User credentials", value = "User credentials") @RequestBody AuthenticationJwtRequest request) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(), new ArrayList<>())
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
    @ApiOperation(value="Post create new account")
    @PostMapping({"/newaccount", "/newaccount/"})
    public UserApp createNewAccount(@ApiParam(name = "User credentials", value = "User credentials") @RequestBody UserAppDto userDtoToCreate) throws BadCredentialsException {
        if (userService.isUsernameExist(userDtoToCreate.getUsername())){
            throw new BadCredentialsException("Username exist in our database, please select different",
                    new AuthenticationJwtRequest(userDtoToCreate.getUsername(), userDtoToCreate.getPassword()));
        }
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
    @PreAuthorize("isAuthenticated()")
    @Operation(security = { @SecurityRequirement(name = JWT_TOKEN_NAME_SWAGGER) })
    @ApiOperation(value="Delete account")
    @DeleteMapping({"/deleteaccount", "/deleteaccount/"})
    public UserAppDeletedResponse deleteAccount(
            @ApiParam(name = "User credentials", value = "User credentials") @RequestBody AuthenticationJwtRequest userDtoToDelete,
                                                @ApiParam(hidden = true) Principal principal) throws BadCredentialsException {
        UserAppDto deletedUser;

        try {
            UserApp userToDelete;
            if (!principal.getName().equals(userDtoToDelete.getUsername())){
                throw new BadCredentialsException("Username isn't the same as users what you wont delete",
                        userDtoToDelete);
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDtoToDelete.getUsername(), userDtoToDelete.getPassword(), new ArrayList<>())
            );

            userToDelete = userService.getUserByUsername(userDtoToDelete.getUsername());

            deletedUser = UserAppDto.builder()
                    .username(userDtoToDelete.getUsername())
                    .email(userToDelete.getEmail())
                    .password(userDtoToDelete.getPassword())
                    .build();

            userService.deleteUserById(userToDelete.getId());

        }
        catch (Exception e){
            throw new BadCredentialsException(
                    "User dont exists in db",
                    userDtoToDelete);
        }

        return new UserAppDeletedResponse(
                "Deleted was sucessfull",
                deletedUser);
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

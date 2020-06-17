package com.clock.clockapi.controller;

import com.clock.clockapi.security.JwtUtil;
import com.clock.clockapi.security.exception.BadCredentialsException;
import com.clock.clockapi.security.mapper.UserAppMapper;
import com.clock.clockapi.security.model.AuthenticationJwtRequest;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.security.model.UserAppDto;
import com.clock.clockapi.services.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtil jwtTokenUtil;

    @Mock
    UserServiceImpl userService;

    @Mock
    UserAppMapper userAppMapper;


    @InjectMocks
    AuthenticationController authenticationController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void createJwtToken() throws Exception {
        //  when
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("username", "password");
        AuthenticationJwtRequest request = new AuthenticationJwtRequest("username", "password");

        // then
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userService.loadUserByUsername(anyString())).thenReturn(new UserApp());
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("Token");

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[?(@.jwt == 'Token')]", hasSize(1)));
    }

    @Test
    void createJwtToken_bedCredentials() throws Exception {
        //  when
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("username", "password");
        AuthenticationJwtRequest request = new AuthenticationJwtRequest("username", "password");

        // then
        when(authenticationManager.authenticate(any())).thenThrow(new CredentialsExpiredException("Error"));

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("[?(@.message == 'Error')]", hasSize(1)))
                .andExpect(jsonPath("[?(@.userCredentials.username == 'username')]", hasSize(1)))
                .andExpect(jsonPath("[?(@.userCredentials.password == 'password')]", hasSize(1)));
    }

    @Test
    void createNewAccount() throws Exception {
        UserAppDto userAppDto = UserAppDto.builder()
                .username("admin")
                .password("admin2")
                .email("ex@gm.com").build();

        UserApp userApp = UserApp.builder()
                .username("admin")
                .password("admin2")
                .email("ex@gm.com")
                .id("ID").build();

        when(userAppMapper.UserAppDtoToUserApp(any(UserAppDto.class))).thenReturn(userApp);
        when(userService.saveUser(any(UserApp.class))).thenReturn(userApp);

        mockMvc.perform(post("/api/newaccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userAppDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[?(@.id == 'ID')]", anything()))
                .andExpect(jsonPath("[?(@.username == 'username')]", anything()))
                .andExpect(jsonPath("[?(@.password == 'admin2')]", anything()))
                .andExpect(jsonPath("[?(@.accountNonExpired == true)]", anything()))
                .andExpect(jsonPath("[?(@.accountNonLocked == true)]", anything()))
                .andExpect(jsonPath("[?(@.credentialsNonExpired == true)]", anything()))
                .andExpect(jsonPath("[?(@.enabled == true)]", anything()))
                .andExpect(jsonPath("[?(@.email == true)]", anything()))
                .andExpect(jsonPath("[?(@.authorities == true)]", anything()));
    }

    @Test
    void createNewAccount_notExpectedCredentials() throws Exception {
        AuthenticationJwtRequest request = new AuthenticationJwtRequest("usernmae", "password");

        when(userAppMapper.UserAppDtoToUserApp(any(UserAppDto.class))).thenReturn(new UserApp());
        when(userService.saveUser(any(UserApp.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/newaccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("[?(@.message == 'Username or password is incorrect witch ours norms or some errors while validation')]", anything()))
                .andExpect(jsonPath("[?(@.userCredentials.username == 'username')]", anything()))
                .andExpect(jsonPath("[?(@.userCredentials.password == 'password')]", anything()));
    }

    @Test
    void deleteAccount() {
//        TODO: implement it
    }

    @Test
    void deleteAccount_noPermission() {
//        TODO: implement it
    }

    @Test
    void deleteAccount_BadCredentials() {
//        TODO: implement it
    }
}
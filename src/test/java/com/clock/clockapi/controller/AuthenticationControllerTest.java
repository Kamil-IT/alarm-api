package com.clock.clockapi.controller;

import com.clock.clockapi.security.JwtUtil;
import com.clock.clockapi.security.mapper.UserAppMapper;
import com.clock.clockapi.security.model.AuthenticationJwtRequest;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.security.model.UserAppDto;
import com.clock.clockapi.services.UserServiceImpl;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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

    UserAppDto userAppDto;
    UserApp userApp;

    @BeforeEach
    void setUp() {
        userAppDto = UserAppDto.builder()
                .username("admin")
                .password("admin2")
                .email("ex@gm.com").build();

        userApp = UserApp.builder()
                .username("admin")
                .password("admin2")
                .email("ex@gm.com")
                .id("ID").build();

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
    void createJwtToken_noAuthentication() throws Exception {

        AuthenticationJwtRequest requestCredentials =
                new AuthenticationJwtRequest(userApp.getUsername(), userApp.getPassword());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthenticationException("message"){});

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestCredentials)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createNewAccount_usernameExist() throws Exception {
        AuthenticationJwtRequest requestCredentials =
                new AuthenticationJwtRequest(userApp.getUsername(), userApp.getPassword());

        when(userService.isUsernameExist(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/newaccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestCredentials)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteAccount_BadCredentials() throws Exception {
        mockMvc.perform(post("/api/deleteaccount")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteAccount() {
//        TODO: implement it
    }

    @Test
    void createNewAccount_bedCredentials() {
//        TODO: implement it
    }

    @Test
    void handleBadCredentialsException() {
//        TODO: implement it
    }
}
package com.clock.clockapi.security.config;

import com.clock.clockapi.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("default")
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
public class SecurityConfigurerLocal extends WebSecurityConfigurerAdapter {

    public static final String AUTH_ENDPOINT = "/api/auth";
    public static final String CREATE_NEW_USER_ENDPOINT = "/api/newaccount";
    public static final String H2_CONSOLE_ENDPOINT = "/h2-console/**";
    public static final String API_DOCS_JSON_SWAGGER_ENDPOINT = "/v2/api-docs";

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticationManager authenticationManager;


    public SecurityConfigurerLocal(@Qualifier("userServiceImpl") UserDetailsService userDetailsService,
                                   JwtRequestFilter jwtRequestFilter, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                .antMatchers(CREATE_NEW_USER_ENDPOINT).permitAll()
                .antMatchers(API_DOCS_JSON_SWAGGER_ENDPOINT).permitAll()
//                Swagger paths
                .antMatchers("/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers().permitAll()
                .antMatchers(H2_CONSOLE_ENDPOINT).permitAll()
                .antMatchers("/console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .headers().frameOptions().disable();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManager;
    }
}

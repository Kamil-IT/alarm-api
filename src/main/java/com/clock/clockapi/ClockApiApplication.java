package com.clock.clockapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableWebMvc
@EnableOpenApi
@SpringBootApplication
public class ClockApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClockApiApplication.class, args);
    }

}

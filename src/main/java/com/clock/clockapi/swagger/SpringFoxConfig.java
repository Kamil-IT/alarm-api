package com.clock.clockapi.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket alarmApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(new ApiInfo(
                        "Api Alarm",
                        "REST API for alarm app, with send JSON as response and use JWT authentication",
                        "v1",
                        "https://github.com/Kamil-IT",
                        new Contact("Kamil", "https://github.com/Kamil-IT", "kkwolny@vp.pl"),
                        "",
                        "",
                        Collections.emptyList()
                ));
    }
}

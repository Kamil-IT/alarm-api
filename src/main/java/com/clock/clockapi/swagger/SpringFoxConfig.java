package com.clock.clockapi.swagger;

import com.clock.clockapi.api.v1.model.alarm.Alarm;
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
                .ignoredParameterTypes(Alarm.class)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(new ApiInfo(
                        "Api Alarm",
                        "REST API for alarm app, with send JSON as response and use JWT authentication \n Android alarm application: https://play.google.com/store/apps/details?id=com.devcivil.alarm_app",
                        "v1",
                        "https://github.com/Kamil-IT",
                        new Contact("Kamil", "https://github.com/Kamil-IT", "kkwolny@vp.pl"),
                        "",
                        "",
                        Collections.emptyList()
                ));
    }

}

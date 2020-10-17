package com.clock.clockapi.swagger;

import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Configuration
public class SpringFoxConfig {

    public static final String JWT_TOKEN_NAME_SWAGGER = "JwtToken";

    @Bean
    public Docket alarmApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Alarm.class, Principal.class)
                .select()
                .paths(input -> {
                    if (input.endsWith("/")){
                        return false;
                    }
                    AntPathMatcher matcher = new AntPathMatcher();
                    return matcher.match("/api/**", input);
                })
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiKey apiKey() {
        return new ApiKey(JWT_TOKEN_NAME_SWAGGER, "Authorization ADD \"Bearer \" BEFORE API KEY !!!", In.HEADER.name());
    }

    /**
     * Set paths to authenticate
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(input -> {
                    AntPathMatcher matcher = new AntPathMatcher();
                    return matcher.match("/api/v1/**", input) ||
                            matcher.match("/api/user/**", input) ||
                            matcher.match("/api/deleteaccount/**", input);
                }).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessPersonalData");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Bearer",
                authorizationScopes));
    }

//    @Bean
//    SecurityConfiguration security() {
//        return new SecurityConfiguration(
//                "test-app-client-id",
//                "test-app-client-secret",
//                "test-app-realm",
//                "test-app",
//                "",
//                ApiKeyVehicle.HEADER,
//                "Authorization",
//                "," /*scope separator*/);
//    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Api Alarm",
                "REST API for alarm app, with send JSON as response and use JWT authentication \n Android alarm application: https://play.google.com/store/apps/details?id=com.devcivil.alarm_app",
                "v1",
                "https://github.com/Kamil-IT",
                new Contact("Kamil", "https://github.com/Kamil-IT", "kkwolny@vp.pl"),
                "",
                "",
                Collections.emptyList()
        );
    }

}

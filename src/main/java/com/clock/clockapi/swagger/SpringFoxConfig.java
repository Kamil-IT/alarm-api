package com.clock.clockapi.swagger;

import com.clock.clockapi.api.v1.model.alarm.Alarm;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Configuration
public class SpringFoxConfig {

    public static final String JWT_TOKEN_NAME_SWAGGER = "Authorization";

    @Bean
    public Docket alarmApi() {
        return new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(Alarm.class, Principal.class)
                .select()
                .paths(input -> {
                    if (input.endsWith("/")) {
                        return false;
                    }
                    AntPathMatcher matcher = new AntPathMatcher();
                    return matcher.match("/api/**", input);
                })
                .build()
                .securitySchemes(List.of(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", AUTHORIZATION, In.HEADER.name());
    }

    /**
     * Set paths to authenticate
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .operationSelector(operationContext -> {
                    AntPathMatcher matcher = new AntPathMatcher();
                    return matcher.match("/api/v1/**", operationContext.requestMappingPattern()) ||
                            matcher.match("/api/user/**", operationContext.requestMappingPattern()) ||
                            matcher.match("/api/deleteaccount/**", operationContext.requestMappingPattern());
                })
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessPersonalData");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Bearer",
                authorizationScopes));
    }

    private ApiInfo apiInfo() {
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

    @Controller
    public static class SpringConfig {

        @GetMapping({"/swagger-ui", "/swagger-ui/"})
        public RedirectView mapPathToSwagger() {
            return new RedirectView("/swagger-ui/index.html");
        }
    }

}

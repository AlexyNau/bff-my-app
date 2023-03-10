package com.example.api.bffmyapp.configuration;

import com.example.api.bffmyapp.configuration.module.CustomPageModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class OpenAPIDocumentationConfig {

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("v1.0")
                .pathsToMatch("/v1.0/**")
                .build();
    }

    @Bean
    public OpenAPI bffMyAppOpenAPI() {
        // No security
        return new OpenAPI()
                .info(new Info().title("API BFF My App")
                        .description("A simple description for bff api.")
                        .version("1.0")
                        .license(new License().name("Issue Github 814").url("https://github.com/spring-cloud/spring-cloud-openfeign/issues/814")));
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setTimeZone(TimeZone.getTimeZone("Europe/Paris"))
                .setLocale(Locale.FRENCH)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
                .registerModules(
                        new PageJacksonModule() // change to "new CustomPageModule()" to test my early fix (not OK for sort properties)
                )
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> {
            if (Boolean.TRUE.equals(configProperties.getTomcat().getEnableAjp()) && server instanceof TomcatServletWebServerFactory) {
                server.addAdditionalTomcatConnectors(redirectConnector());
            }
        };
    }

    private Connector redirectConnector() {
        var connector = new Connector("AJP/1.3");
        connector.setScheme("http");
        connector.setPort(configProperties.getTomcat().getAjpPort());
        connector.setSecure(false);
        connector.setAllowTrace(true);
        ((AbstractAjpProtocol<?>) connector.getProtocolHandler()).setSecretRequired(false);

        return connector;
    }
}

package com.example.api.bffmyapp.configuration;

import com.example.api.bffmyapp.configuration.properties.Tomcat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Getter
@Setter
@ConfigurationProperties(prefix = "api")
@ConfigurationPropertiesScan
public class ConfigProperties {
    private Tomcat tomcat;
}

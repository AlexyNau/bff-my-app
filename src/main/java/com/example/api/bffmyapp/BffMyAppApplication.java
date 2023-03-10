package com.example.api.bffmyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan("com.example.api.bffmyapp.configuration")
public class BffMyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BffMyAppApplication.class, args);
	}

}

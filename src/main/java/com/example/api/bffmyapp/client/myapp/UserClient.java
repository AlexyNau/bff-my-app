package com.example.api.bffmyapp.client.myapp;

import com.example.api.bffmyapp.client.ClientApiConfig;
import com.example.api.bffmyapp.dto.client.myapp.UserDTO;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "UserClient", url = "${api.myapp.url}/users", configuration = ClientApiConfig.class)
public interface UserClient {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<UserDTO> listUser(@RequestParam(required = false, name = "last-name") String lastName,
                                           @RequestParam(required = false, name = "first-name") String firstName,
                                           @RequestParam(required = false, name = "email") String email,
                                           @ParameterObject Pageable pageable);
}

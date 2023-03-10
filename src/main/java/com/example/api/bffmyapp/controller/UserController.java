package com.example.api.bffmyapp.controller;

import com.example.api.bffmyapp.api.UserApi;
import com.example.api.bffmyapp.client.myapp.UserClient;
import com.example.api.bffmyapp.dto.User;
import com.example.api.bffmyapp.dto.client.myapp.UserDTO;
import com.example.api.bffmyapp.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UserController implements UserApi {
    @Autowired
    private UserClient userClient;
    private UserMapper userMapper;

    @Override
    public ResponseEntity<Page<User>> listUser(String lastName, String firstName, String email, Pageable pageable) {

        Page<UserDTO> userDTOS = userClient.listUser(lastName, firstName, email, pageable);

        // By using PageJacksonModule (in com.example.api.bffmyapp.configuration.OpenAPIDocumentationConfig.objectMapper), we've got' :
        userDTOS.getTotalElements(); // 5, should be 11
        userDTOS.getSize(); // 5, OK
        userDTOS.getTotalPages(); // 1, should be 3
        userDTOS.getSort().isSorted(); // false, should be true
        userDTOS.isLast(); // true, should be false

        // You can try to switch to com.example.api.bffmyapp.configuration.module.CustomPageModule
        // in the ObjectMapper configuration to see my early fix

        Page<User> users = userDTOS.map(entity -> userMapper.userDTOToUser(entity));
        return ResponseEntity.ok(users);
    }
}

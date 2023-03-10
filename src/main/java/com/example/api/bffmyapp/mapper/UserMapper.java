package com.example.api.bffmyapp.mapper;

import com.example.api.bffmyapp.configuration.MapStructDefaultConfig;
import com.example.api.bffmyapp.dto.User;
import com.example.api.bffmyapp.dto.client.myapp.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructDefaultConfig.class)
public interface UserMapper extends AbstractMapper<List<UserDTO>, List<User>>{

    List<User> toDTO(List<UserDTO> userEntities);

    User userDTOToUser(UserDTO userEntity);
}

package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.user.UserDto;
import com.example.dto.user.UserRegistrationRequestDto;
import com.example.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}

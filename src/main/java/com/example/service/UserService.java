package com.example.service;

import com.example.dto.user.UserDto;
import com.example.dto.user.UserRegistrationRequestDto;

public interface UserService {
    UserDto register(UserRegistrationRequestDto userRegistrationRequestDto);
}

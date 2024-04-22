package com.example.service;

import com.example.dto.user.UserDto;
import com.example.dto.user.UserRegistrationRequestDto;

public interface AuthenticationService {
    UserDto register(UserRegistrationRequestDto userRegistrationRequestDto);

    boolean isUserEmailExists(String email);
}

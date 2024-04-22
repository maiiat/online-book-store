package com.example.service.impl;

import com.example.dto.user.UserDto;
import com.example.dto.user.UserRegistrationRequestDto;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.user.UserRepository;
import com.example.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = userMapper.toModel(userRegistrationRequestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public boolean isUserEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}

package com.example.service.impl;

import com.example.dto.user.UserDto;
import com.example.dto.user.UserRegistrationRequestDto;
import com.example.exception.EmailAlreadyExistException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.user.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        checkIfEmailExistsOrThrowException(userRegistrationRequestDto.email());
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.password()));
        user.setEmail(userRegistrationRequestDto.email());
        user.setFirstName(userRegistrationRequestDto.firstName());
        user.setLastName(userRegistrationRequestDto.lastName());
        user.setShippingAddress(userRegistrationRequestDto.shippingAddress());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void checkIfEmailExistsOrThrowException(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyExistException("Such email already exist: " + email);
        }
    }
}

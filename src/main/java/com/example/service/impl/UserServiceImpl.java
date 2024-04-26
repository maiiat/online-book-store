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
    public UserDto register(UserRegistrationRequestDto uaer) {
        checkIfEmailExists(uaer.email());
        User user = new User();
        user.setPassword(passwordEncoder.encode(uaer.password()));
        user.setEmail(uaer.email());
        user.setFirstName(uaer.firstName());
        user.setLastName(uaer.lastName());
        user.setShippingAddress(uaer.shippingAddress());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void checkIfEmailExists(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyExistException("Such email already exist: " + email);
        }
    }
}

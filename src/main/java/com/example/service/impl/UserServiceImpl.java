package com.example.service.impl;

import com.example.dto.user.UserDto;
import com.example.dto.user.UserRegistrationRequestDto;
import com.example.exception.RegistrationException;
import com.example.mapper.UserMapper;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.role.RoleRepository;
import com.example.repository.user.UserRepository;
import com.example.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDto register(UserRegistrationRequestDto userRequest) {
        checkIfEmailAlreadyExists(userRequest.email());
        User user = userMapper.toModel(userRequest);
        user.setRoles(Set.of(roleRepository.findAllByName(Role.RoleName.USER.name()).get()));
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new RegistrationException("Such email already exist: " + email);
        }
    }
}

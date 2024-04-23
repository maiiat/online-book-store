package com.example.dto.user;

import com.example.validation.Email;
import com.example.validation.FieldMatch;
import com.example.validation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "repeatedPassword",
        message = "Password and repeated password must be equal"
)
public record UserRegistrationRequestDto(
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Password String password,
        @NotBlank String repeatedPassword,
        @NotBlank @Size(max = 255) String firstName,
        @NotBlank @Size(max = 255) String lastName,
        @Size(max = 255)
        String shippingAddress
) {
}

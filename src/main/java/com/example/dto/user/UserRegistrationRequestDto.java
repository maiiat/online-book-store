package com.example.dto.user;

import com.example.validation.Email;
import com.example.validation.FieldMatch;
import com.example.validation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "repeatedPassword",
        message = "fields: 'password' and 'repeatedPassword' must be equal"
)
public record UserRegistrationRequestDto(
        @Schema(description = "Standard email format", example = "jon.smith@gmail.com")
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(min = 8, max = 25) @Password String password,
        @NotBlank String repeatedPassword,
        @NotBlank @Size(max = 255) String firstName,
        @NotBlank @Size(max = 255) String lastName,
        @Size(max = 255)
        String shippingAddress
) {
}

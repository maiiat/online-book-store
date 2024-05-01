package com.example.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
        @Schema(description = "Standard email format", example = "jon.smith@gmail.com")
        @NotBlank String email,
        @NotBlank String password
) {
}

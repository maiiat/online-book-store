package com.example.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUpdateCategoryRequestDto(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 255) String description) {
}

package com.example.dto.book;

import com.example.validation.Isbn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateUpdateBookRequestDto(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 255) String author,
        @Schema(description = "Standard ISBN format", example = "978-3-16-148410-0")
        @NotBlank @Isbn String isbn,
        @Size(max = 255) String description,
        @Size(max = 255) String coverImage,
        @NotNull @Positive BigDecimal price
) {
}

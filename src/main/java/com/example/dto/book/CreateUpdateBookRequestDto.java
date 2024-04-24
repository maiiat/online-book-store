package com.example.dto.book;

import com.example.validation.Isbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateUpdateBookRequestDto(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 255) String author,
        @NotBlank @Isbn String isbn,
        @Size(max = 255) String description,
        @Size(max = 255) String coverImage,
        @Positive BigDecimal price
) {
}

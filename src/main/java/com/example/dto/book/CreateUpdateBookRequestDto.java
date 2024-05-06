package com.example.dto.book;

import com.example.validation.Isbn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class CreateUpdateBookRequestDto {
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String author;

    @Schema(description = "Standard ISBN format", example = "978-3-16-148410-0")
    @NotBlank
    @Isbn
    private String isbn;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String coverImage;

    @NotNull
    @Positive
    private BigDecimal price;

    private Set<Long> categoryIds;
}

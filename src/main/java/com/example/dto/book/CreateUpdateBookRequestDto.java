package com.example.dto.book;

import com.example.validation.Isbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateUpdateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    @Isbn
    private String isbn;
    private String description;
    private String coverImage;
    @NotBlank
    @Positive
    private BigDecimal price;
}

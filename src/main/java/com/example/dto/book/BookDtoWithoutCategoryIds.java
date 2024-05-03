package com.example.dto.book;

import java.math.BigDecimal;

public record BookDtoWithoutCategoryIds(
        Long id,
        String title,
        String author,
        String isbn,
        String description,
        String coverImage,
        BigDecimal price
) {
}

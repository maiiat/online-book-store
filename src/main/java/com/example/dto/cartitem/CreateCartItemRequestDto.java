package com.example.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @Positive
    @NotNull
    private Long bookId;
    @Positive
    private Integer quantity;
}

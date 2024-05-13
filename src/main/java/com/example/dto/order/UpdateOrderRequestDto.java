package com.example.dto.order;

import com.example.model.Order;
import com.example.validation.Enum;
import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequestDto(
        @NotBlank @Enum(enumClass = Order.Status.class) String status) {
}

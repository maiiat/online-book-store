package com.example.controller;

import com.example.dto.order.CreateOrderRequestDto;
import com.example.dto.order.OrderItemResponseDto;
import com.example.dto.order.OrderResponseDto;
import com.example.dto.order.UpdateOrderRequestDto;
import com.example.model.User;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management",
        description = "Endpoints for managing orders and order items")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Retrieve user's order history",
            description = "Retrieve user's order history")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<OrderResponseDto> getOrderHistory(
            Authentication authentication,
            Pageable pageable
    ) {
        return orderService.getOrderHistory(getAuthenticatedUser(authentication), pageable);
    }

    @Operation(summary = "Retrieve all Order Items for a specific order",
            description = "Retrieve all Order Items for a specific order per user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    public Set<OrderItemResponseDto> getOrderHistoryById(
            Authentication authentication,
            @PathVariable Long orderId
    ) {
        return orderService.getOrderItemsByOrderId(getAuthenticatedUser(authentication), orderId);
    }

    @Operation(summary = "Retrieve a specific Order Item within an order",
            description = "Retrieve a specific Order Item within an order per user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItemByIdByOrderId(
            Authentication authentication,
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.getOrderItemByIdByOrderId(
            getAuthenticatedUser(authentication), orderId, itemId);
    }

    @Operation(summary = "Place an order",
            description = "Place an order")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponseDto createCartItem(
            Authentication authentication,
            @RequestBody @Valid CreateOrderRequestDto requestDto
    ) {
        return orderService.createOrder(getAuthenticatedUser(authentication), requestDto);
    }

    @Operation(summary = "Update order status",
            description = "Update status by order id, current options: "
            + "DELIVERED, PENDING, COMPLETED")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderResponseDto updateOrderStatusById(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderRequestDto requestDto
    ) {

        return orderService.updateOrderStatusById(
                getAuthenticatedUser(authentication), id, requestDto);
    }

    private User getAuthenticatedUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}

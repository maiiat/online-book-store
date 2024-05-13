package com.example.service;

import com.example.dto.order.CreateOrderRequestDto;
import com.example.dto.order.OrderItemResponseDto;
import com.example.dto.order.OrderResponseDto;
import com.example.dto.order.UpdateOrderRequestDto;
import com.example.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderResponseDto> getOrderHistory(User user, Pageable pageable);

    Set<OrderItemResponseDto> getOrderItemsByOrderId(User user, Long orderId);

    OrderItemResponseDto getOrderItemByIdByOrderId(User user, Long orderId, Long itemId);

    OrderResponseDto createOrder(User user, CreateOrderRequestDto requestDto);

    OrderResponseDto updateOrderStatusById(User user, Long id, UpdateOrderRequestDto requestDto);
}

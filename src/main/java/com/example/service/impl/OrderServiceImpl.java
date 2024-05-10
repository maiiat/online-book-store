package com.example.service.impl;

import com.example.dto.order.CreateOrderRequestDto;
import com.example.dto.order.OrderItemResponseDto;
import com.example.dto.order.OrderResponseDto;
import com.example.dto.order.UpdateOrderRequestDto;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.OrderItemMapper;
import com.example.mapper.OrderMapper;
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.model.ShoppingCart;
import com.example.model.User;
import com.example.repository.cartitem.CartItemRepository;
import com.example.repository.order.OrderItemRepository;
import com.example.repository.order.OrderRepository;
import com.example.repository.shoppingcartrepository.ShoppingCartRepository;
import com.example.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<OrderResponseDto> getOrderHistory(User user, Pageable pageable) {
        return orderRepository.findOrdersByUser(user, pageable).stream()
            .map(orderMapper::toDto)
            .toList();
    }

    @Override
    public Set<OrderItemResponseDto> getOrderItemsByOrderId(User user, Long orderId) {
        Order order = getOrderByIdAndUser(user,orderId);

        return order.getOrderItems().stream()
            .map(orderItemMapper::toDto)
            .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getOrderItemByIdByOrderId(
            User user,
            Long orderId,
            Long orderItemId) {
        Order order = getOrderByIdAndUser(user,orderId);

        return orderItemMapper.toDto(
            orderItemRepository.findOrderItemByOrderIdAndId(order.getId(), orderItemId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find item by id:" + orderItemId)));
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(User user, CreateOrderRequestDto requestDto) {
        Order order = orderMapper.toEntity(requestDto);

        Set<CartItem> cartItems = getCartItems(getShoppingCart(user.getId()));
        Set<OrderItem> orderItems = getOrderItemsFromShoppingCart(cartItems, order);
        BigDecimal total = calculateTotal(cartItems);

        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setTotal(total);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.saveAndFlush(order);

        cartItemRepository.deleteByIdAndShoppingCartId(user.getId());
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatusById(
            User user,
            Long orderId,
            UpdateOrderRequestDto requestDto) {
        Order order = getOrderById(orderId);
        Order.Status status = Order.Status.valueOf(requestDto.status().toUpperCase());
        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("Cannot find shopping cart by id: " + userId));
    }

    private Set<CartItem> getCartItems(ShoppingCart shoppingCart) {
        Set<CartItem> cartItems = shoppingCart.getCartItems();

        if (cartItems == null || cartItems.isEmpty()) {
            throw new EntityNotFoundException(
                String.format("Shopping cart of: %s user is empty",
                    shoppingCart.getUser().getEmail())
            );
        }
        return cartItems;
    }

    private Order getOrderByIdAndUser(User user, Long orderId) {
        return orderRepository.findByIdAndUser(orderId, user).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Cannot find order by id: %s for username: %s",
                    orderId, user.getEmail())));
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new EntityNotFoundException("Cannot find order by id: " + orderId));
    }

    private Set<OrderItem> getOrderItemsFromShoppingCart(Set<CartItem> cartItems, Order order) {
        return cartItems.stream()
            .map(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setBook(item.getBook());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getBook().getPrice());
                return orderItem;
            })
            .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
            .map(bd -> bd.getBook().getPrice().multiply(BigDecimal.valueOf(bd.getQuantity())))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }
}

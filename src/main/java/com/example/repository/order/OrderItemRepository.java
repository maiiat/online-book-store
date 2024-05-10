package com.example.repository.order;

import com.example.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findOrderItemByOrderIdAndId(Long orderId, Long itemId);
}

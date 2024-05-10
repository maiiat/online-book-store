package com.example.repository.order;

import com.example.model.Order;
import com.example.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Page<Order> findOrdersByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findByIdAndUser(Long orderId, User user);

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findById(Long orderId);
}

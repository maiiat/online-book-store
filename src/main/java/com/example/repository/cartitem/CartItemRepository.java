package com.example.repository.cartitem;

import com.example.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = {"shoppingCart", "book"})
    Optional<CartItem> findByIdAndShoppingCartIdAndIsDeletedFalse(
                Long cartItemId, Long shoppingCartId);
}

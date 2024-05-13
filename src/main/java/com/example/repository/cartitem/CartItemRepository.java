package com.example.repository.cartitem;

import com.example.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = {"shoppingCart", "book"})
    Optional<CartItem> findByIdAndShoppingCartIdAndIsDeletedFalse(
                Long cartItemId, Long shoppingCartId);

    @Modifying
    @Query("UPDATE CartItem c SET c.isDeleted = true WHERE c.shoppingCart.id = :userId")
    void deleteByIdAndShoppingCartId(Long userId);
}

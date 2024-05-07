package com.example.service;

import com.example.dto.cartitem.CreateCartItemRequestDto;
import com.example.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.model.ShoppingCart;
import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(Long userId);

    ShoppingCartResponseDto addCartItem(Long userId,
                                        CreateCartItemRequestDto requestDto);

    ShoppingCartResponseDto updateCartItem(Long userId,
                                           Long cartItemId,
                                           UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long userId, Long cartItemId);

    ShoppingCart createShoppingCart(User user);
}

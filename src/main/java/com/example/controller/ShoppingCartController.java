package com.example.controller;

import com.example.dto.cartitem.CreateCartItemRequestDto;
import com.example.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.model.User;
import com.example.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management",
        description = "Endpoints for managing shoppingCart and cartItem")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Add cart item to user's cart",
            description = "Create and add cart item to user's cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShoppingCartResponseDto createCartItem(
            Authentication authentication,
            @RequestBody @Valid CreateCartItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addCartItem(user.getId(), requestDto);
    }

    @Operation(summary = "Get all shopping cart's items",
            description = "Get all shopping cart's items for user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartResponseDto getAllCartItems(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUserId(user.getId());
    }

    @Operation(summary = "Update cart item by id",
            description = "Update cart item by id from user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartResponseDto updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCartItem(user.getId(), cartItemId, requestDto);
    }

    @Operation(summary = "Delete cart item by id",
            description = "Delete cart item by id from user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    public void deleteCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId
    ) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItem(user.getId(), cartItemId);
    }
}

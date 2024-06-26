package com.example.service.impl;

import com.example.dto.cartitem.CreateCartItemRequestDto;
import com.example.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.CartItemMapper;
import com.example.mapper.ShoppingCartMapper;
import com.example.model.Book;
import com.example.model.CartItem;
import com.example.model.ShoppingCart;
import com.example.model.User;
import com.example.repository.book.BookRepository;
import com.example.repository.cartitem.CartItemRepository;
import com.example.repository.shoppingcartrepository.ShoppingCartRepository;
import com.example.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartResponseDto getByUserId(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCart(userId));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addCartItem(Long userId, CreateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        Long bookId = requestDto.getBookId();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with id: " + bookId));

        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst()
                .ifPresentOrElse(
                    item -> item.setQuantity(item.getQuantity() + requestDto.getQuantity()),
                    () -> addCartItemToCart(requestDto, book, shoppingCart)
        );

        cartItemRepository.saveAll(shoppingCart.getCartItems());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    ) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        CartItem cartItem = getCartItemOfShoppingCart(shoppingCart.getId(), cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        CartItem cartItem = getCartItemOfShoppingCart(shoppingCart.getId(), cartItemId);
        shoppingCart.removeItemFromCart(cartItem);
    }

    @Override
    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCart;
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("Cannot find shopping cart by id: " + userId));
    }

    private CartItem getCartItemOfShoppingCart(Long shoppingCartId, Long cartItemId) {
        return cartItemRepository.findByIdAndShoppingCartIdAndIsDeletedFalse(
            cartItemId, shoppingCartId)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("No such cartItem with id %d in shopping cart", cartItemId)
            ));
    }

    private void addCartItemToCart(
            CreateCartItemRequestDto requestDto,
            Book book,
            ShoppingCart cart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setBook(book);
        cart.addItemToCart(cartItem);
    }
}

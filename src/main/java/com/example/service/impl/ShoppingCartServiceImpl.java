package com.example.service.impl;

import com.example.dto.cartitem.CartItemResponseDto;
import com.example.dto.cartitem.CreateCartItemRequestDto;
import com.example.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.exception.BookAlreadyAddedToCartException;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.CartItemMapper;
import com.example.mapper.ShoppingCartMapper;
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
    public ShoppingCartResponseDto addCartItem(Long userId, CreateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        checkIfBookExist(requestDto);

        if (shoppingCart.getCartItems().stream()
                .anyMatch(item -> item.getBook().getId().equals(requestDto.getBookId()))) {
            throw new BookAlreadyAddedToCartException("Book already added in shopping cart");
        }

        cartItem.setShoppingCart(shoppingCart);
        CartItem save = cartItemRepository.save(cartItem);
        CartItemResponseDto dto = cartItemMapper.toDto(save);
        dto.setBookTitle(bookRepository.findById(dto.getBookId()).orElseThrow().getTitle());

        ShoppingCartResponseDto resultDto = shoppingCartMapper.toDto(shoppingCart);
        resultDto.getCartItems().add(dto);
        return resultDto;
    }

    @Override
    public ShoppingCartResponseDto updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    ) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        CartItem cartItem = getCartItemOfShoppingCart(shoppingCart, cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        cartItemRepository.deleteById(getCartItemOfShoppingCart(shoppingCart, cartItemId).getId());
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

    private CartItem getCartItemOfShoppingCart(ShoppingCart shoppingCart, Long cartItemId) {
        return cartItemRepository.findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("No such cartItem with id %d in shopping cart", cartItemId)
            ));
    }

    private void checkIfBookExist(CreateCartItemRequestDto createCartItemRequestDto) {
        Long bookId = createCartItemRequestDto.getBookId();
        bookRepository.findById(bookId).orElseThrow(()
                -> new EntityNotFoundException("There is no book with '" + bookId + "' id"));
    }
}

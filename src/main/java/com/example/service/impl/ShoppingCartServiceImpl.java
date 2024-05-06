package com.example.service.impl;

import com.example.dto.cartitem.CartItemResponseDto;
import com.example.dto.cartitem.CreateCartItemRequestDto;
import com.example.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.exception.BookAlreadyAddedToCartException;
import com.example.exception.CartItemAbsenceException;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.CartItemMapper;
import com.example.mapper.ShoppingCartMapper;
import com.example.model.CartItem;
import com.example.model.ShoppingCart;
import com.example.repository.book.BookRepository;
import com.example.repository.cartitem.CartItemRepository;
import com.example.repository.shoppingcartrepository.ShoppingCartRepository;
import com.example.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        checkCartItemsInUsersShoppingCart(userId, cartItemId);
        CartItem cartItemFromDb = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id " + cartItemId));

        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItemFromDb.setQuantity(cartItem.getQuantity());
        cartItemRepository.save(cartItemFromDb);
        return shoppingCartMapper.toDto(getShoppingCart(userId));
    }

    @Override
    public ShoppingCartResponseDto deleteCartItem(Long userId, Long cartItemId) {
        checkCartItemsInUsersShoppingCart(userId, cartItemId);
        cartItemRepository.deleteById(cartItemId);
        return shoppingCartMapper.toDto(getShoppingCart(userId));
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("Cannot find shopping cart by id: " + userId));
    }

    private void checkCartItemsInUsersShoppingCart(Long userId, Long cartItemId) {
        if (getShoppingCart(userId).getCartItems()
                .stream()
                .noneMatch(item -> item.getId().equals(cartItemId))) {
            throw new CartItemAbsenceException("There is no '"
                + cartItemId + "' cart item id in user's shopping cart");
        }
    }

    private void checkIfBookExist(CreateCartItemRequestDto createCartItemRequestDto) {
        Long bookId = createCartItemRequestDto.getBookId();
        bookRepository.findById(bookId).orElseThrow(()
                -> new EntityNotFoundException("There is no book with '" + bookId + "' id"));
    }
}

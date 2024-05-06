package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.cartitem.CartItemResponseDto;
import com.example.dto.cartitem.CreateCartItemRequestDto;
import com.example.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mappings({
            @Mapping(source = "book.id", target = "bookId"),
            @Mapping(source = "book.title", target = "bookTitle")
    })
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookFromId")
    CartItem toModel(CreateCartItemRequestDto requestDto);

    @Mapping(target = "book", ignore = true)
    CartItem toModel(UpdateCartItemRequestDto requestDto);
}

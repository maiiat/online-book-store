package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "cartItems", target = "cartItems")
    })
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}

package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.order.CreateOrderRequestDto;
import com.example.dto.order.OrderResponseDto;
import com.example.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);

    Order toEntity(CreateOrderRequestDto requestDto);
}

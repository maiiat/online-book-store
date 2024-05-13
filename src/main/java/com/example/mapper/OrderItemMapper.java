package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.order.OrderItemResponseDto;
import com.example.model.OrderItem;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    Set<OrderItemResponseDto> toDto(Set<OrderItem> orderItems);
}

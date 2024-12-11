package com.purchase.cart.mapper;

import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "productId", target = "productId")
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(source = "productId", target = "productId")
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    List<OrderItemDTO> toDTOList(List<OrderItem> orderItems);
}





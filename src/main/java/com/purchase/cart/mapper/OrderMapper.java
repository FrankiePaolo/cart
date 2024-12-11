package com.purchase.cart.mapper;

import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
    List<OrderDTO> toDTOList(List<Order> orders);
}

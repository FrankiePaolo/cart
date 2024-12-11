package com.purchase.cart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private BigDecimal orderPrice;
    private BigDecimal orderVat;
    private List<OrderItemDTO> items;
}

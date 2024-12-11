package com.purchase.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemsWrapper {
    private List<OrderItemDTO> items;
}
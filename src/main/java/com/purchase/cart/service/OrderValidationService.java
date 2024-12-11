package com.purchase.cart.service;

import com.purchase.cart.dto.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderValidationService {
    private static final int MIN_QUANTITY = 1;

    public void validateOrder(List<OrderItemDTO> items) {
        validateOrderExists(items);
        items.forEach(this::validateOrderItem);
    }

    private void validateOrderExists(List<OrderItemDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
    }

    private void validateOrderItem(OrderItemDTO item) {
        validateProductId(item);
        validateQuantity(item);
    }

    private void validateProductId(OrderItemDTO item) {
        if (item.getProductId() <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive integer");
        }
    }

    private void validateQuantity(OrderItemDTO item) {
        int quantity = item.getQuantity();
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

}
package com.purchase.cart.service;

import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.exception.InvalidOrderException;
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
            throw new InvalidOrderException("Order must contain at least one item");
        }
    }

    private void validateOrderItem(OrderItemDTO item) {
        validateProductId(item);
        validateQuantity(item);
    }

    private void validateProductId(OrderItemDTO item) {
        if (item.getProductId() <= 0) {
            throw new InvalidOrderException("Product ID must be a positive integer");
        }
    }

    private void validateQuantity(OrderItemDTO item) {
        int quantity = item.getQuantity();
        if (quantity < MIN_QUANTITY) {
            throw new InvalidOrderException("Quantity must be greater than zero");
        }
    }

}
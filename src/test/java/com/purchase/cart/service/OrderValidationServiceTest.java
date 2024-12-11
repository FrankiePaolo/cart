package com.purchase.cart.service;


import com.purchase.cart.dto.OrderItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderValidationServiceTest {

    private OrderValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new OrderValidationService();
    }

    @Test
    void validateOrder_WithValidItems_Succeeds() {
        OrderItemDTO validItem = createOrderItem(1, 5);

        assertDoesNotThrow(() ->
                validationService.validateOrder(List.of(validItem))
        );
    }

    @Test
    void validateOrder_WithNullList_ThrowsException() {
        assertThatThrownBy(() ->
                validationService.validateOrder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order must contain at least one item");
    }

    @Test
    void validateOrder_WithEmptyList_ThrowsException() {
        assertThatThrownBy(() ->
                validationService.validateOrder(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order must contain at least one item");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void validateOrder_WithInvalidQuantity_ThrowsException(int invalidQuantity) {
        OrderItemDTO invalidItem = createOrderItem(1, invalidQuantity);

        assertThatThrownBy(() ->
                validationService.validateOrder(List.of(invalidItem)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero");
    }

    @Test
    void validateOrder_WithMultipleValidItems_Succeeds() {
        OrderItemDTO item1 = createOrderItem(1, 5);
        OrderItemDTO item2 = createOrderItem(2, 10);

        assertDoesNotThrow(() ->
                validationService.validateOrder(List.of(item1, item2))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100, 999})
    void validateOrder_WithValidProductIds_Succeeds(int productId) {
        OrderItemDTO validItem = createOrderItem(productId, 5);

        assertDoesNotThrow(() ->
                validationService.validateOrder(List.of(validItem))
        );
    }

    @Test
    void validateOrder_WithZeroProductId_ThrowsException() {
        OrderItemDTO invalidItem = createOrderItem(0, 5);

        assertThatThrownBy(() ->
                validationService.validateOrder(List.of(invalidItem)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product ID must be a positive integer");
    }


    private OrderItemDTO createOrderItem(int productId, int quantity) {
        OrderItemDTO item = new OrderItemDTO();
        item.setProductId(productId);
        item.setQuantity(quantity);
        return item;
    }
}

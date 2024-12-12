package com.purchase.cart.service;

import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.dto.ProductDTO;
import com.purchase.cart.exception.OrderCreationException;
import com.purchase.cart.mapper.OrderItemMapper;
import com.purchase.cart.mapper.OrderMapper;
import com.purchase.cart.model.Order;
import com.purchase.cart.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductService productService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private PriceCalculationService priceCalculationService;

    private OrderService orderService;

    @Mock
    private OrderValidationService orderValidationService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(productService, orderRepository,
                orderMapper, orderItemMapper, priceCalculationService, orderValidationService);
    }
    @Test
    void createOrder_WithMultipleItems_CalculatesTotalCorrectly() {
        // given
        OrderItemDTO item1 = createOrderItemDTO(1, 2);
        OrderItemDTO item2 = createOrderItemDTO(2, 1);

        ProductDTO product1 = createProductDTO("10.00", "0.20");
        ProductDTO product2 = createProductDTO("20.00", "0.20");

        OrderDTO expectedOrderDTO = new OrderDTO();
        expectedOrderDTO.setOrderPrice(new BigDecimal("40.00")); // (10 * 2) + (20 * 1)
        expectedOrderDTO.setOrderVat(new BigDecimal("8.00"));    // 40 * 0.20
        expectedOrderDTO.setItems(List.of(item1, item2));

        when(productService.getId(1)).thenReturn(product1);
        when(productService.getId(2)).thenReturn(product2);
        when(priceCalculationService.calculateItemPrice(eq(item1), eq(product1)))
                .thenReturn(new BigDecimal("20.00"));
        when(priceCalculationService.calculateItemPrice(eq(item2), eq(product2)))
                .thenReturn(new BigDecimal("20.00"));
        when(priceCalculationService.calculateVat(any(), any()))
                .thenReturn(new BigDecimal("4.00"));
        when(orderRepository.save(any())).thenReturn(new Order());
        when(orderMapper.toDTO(any())).thenReturn(expectedOrderDTO);

        // when
        OrderDTO result = orderService.createOrder(List.of(item1, item2));

        // then
        assertThat(result.getOrderPrice()).isEqualByComparingTo("40.00");
        assertThat(result.getOrderVat()).isEqualByComparingTo("8.00");
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems()).containsExactly(item1, item2);
    }

    @Test
    void createOrder_DatabaseError_ThrowsOrderCreationException() {
        // Arrange
        OrderItemDTO item = createOrderItemDTO(1, 2);
        when(orderRepository.save(any())).thenThrow(new RuntimeException("Database connection failed"));

        OrderCreationException exception = assertThrows(OrderCreationException.class, () ->
                orderService.createOrder(List.of(item))
        );
        assertEquals("Failed to create order: Database connection failed", exception.getMessage());
    }

    private OrderItemDTO createOrderItemDTO(int productId, int quantity) {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setProductId(productId);
        itemDTO.setQuantity(quantity);
        return itemDTO;
    }

    private ProductDTO createProductDTO(String price, String vatRate) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal(price));
        productDTO.setVatRate(new BigDecimal(vatRate));
        return productDTO;
    }
}

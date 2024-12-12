package com.purchase.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.exception.GlobalExceptionHandler;
import com.purchase.cart.exception.InvalidOrderException;
import com.purchase.cart.exception.OrderCreationException;
import com.purchase.cart.mapper.OrderMapper;
import com.purchase.cart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({OrderController.class, GlobalExceptionHandler.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    void createOrder_WithValidRequest_ReturnsCreatedOrder() throws Exception {
        // given
        OrderDTO expectedOrder = new OrderDTO();
        expectedOrder.setOrderPrice(new BigDecimal(20));
        expectedOrder.setOrderVat(new BigDecimal(4));
        OrderItemDTO responseItem = new OrderItemDTO();
        responseItem.setProductId(1);
        responseItem.setQuantity(2);
        responseItem.setPrice(new BigDecimal(20));
        responseItem.setVat(new BigDecimal(4));
        expectedOrder.setItems(List.of(responseItem));

        when(orderService.createOrder(any())).thenReturn(expectedOrder);

        String requestJson = """
                {
                    "order": {
                        "items": [
                            {
                                "product_id": 1,
                                "quantity": 2
                            }
                        ]
                    }
                }""";

        // when/then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderPrice").value(20))
                .andExpect(jsonPath("$.orderVat").value(4))
                .andExpect(jsonPath("$.items[0].product_id").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].price").value(20))
                .andExpect(jsonPath("$.items[0].vat").value(4));
    }
    @Test
    void createOrder_InvalidOrder() throws Exception {

        when(orderService.createOrder(any())).thenThrow(new InvalidOrderException("Product ID must be a positive integer"));

        String requestJson = """
                {
                    "order": {
                        "items": [
                            {
                                "product_id": -1,
                                "quantity": 2
                            }
                        ]
                    }
                }""";

        // when/then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("ERR_001"))
                .andExpect(jsonPath("$.message").value("Product ID must be a positive integer"));
    }

    @Test
    void createOrder_OrderCreationFailed() throws Exception {

        when(orderService.createOrder(any())).thenThrow(new OrderCreationException("Order creation failed: Database connection failed"));

        String requestJson = """
                {
                    "order": {
                        "items": [
                            {
                                "product_id": 1,
                                "quantity": 2
                            }
                        ]
                    }
                }""";

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value("ERR_002"))
                .andExpect(jsonPath("$.message").value("Order creation failed: Database connection failed"));
    }

    @Test
    void createOrder_UnexpectedError() throws Exception {
        when(orderService.createOrder(any())).thenThrow(new RuntimeException("Unexpected system error"));

        String requestJson = """
                {
                    "order": {
                        "items": [
                            {
                                "product_id": 1,
                                "quantity": 2
                            }
                        ]
                    }
                }""";

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value("ERR_003"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }

}

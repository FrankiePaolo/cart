package com.purchase.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.dto.OrderItemDTO;
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

@WebMvcTest(OrderController.class)
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
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setProductId(1);
        itemDTO.setQuantity(2);
        itemDTO.setPrice(new BigDecimal("10.00"));
        itemDTO.setVat(new BigDecimal("2.00"));

        OrderDTO expectedOrder = new OrderDTO();
        expectedOrder.setOrderPrice(new BigDecimal("20.00"));
        expectedOrder.setOrderVat(new BigDecimal("4.00"));
        expectedOrder.setItems(List.of(itemDTO));

        when(orderService.createOrder(any())).thenReturn(expectedOrder);

        // when/then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(itemDTO))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderPrice").value("20.00"))
                .andExpect(jsonPath("$.orderVat").value("4.00"))
                .andExpect(jsonPath("$.items[0].productId").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].price").value(20))
                .andExpect(jsonPath("$.items[0].vat").value(4));

    }

    @Test
    void createOrder_WithEmptyRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest());
    }
}
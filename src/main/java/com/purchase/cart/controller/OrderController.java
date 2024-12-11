package com.purchase.cart.controller;

import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.dto.OrderRequestDTO;
import com.purchase.cart.mapper.OrderMapper;
import com.purchase.cart.model.Order;
import com.purchase.cart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        OrderDTO orderDTO = orderService.createOrder(orderRequest.getOrder().getItems());
        return ResponseEntity.ok(orderDTO);
    }
}

package com.purchase.cart.controller;

import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.dto.OrderRequestDTO;
import com.purchase.cart.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        OrderDTO orderDTO = orderService.createOrder(orderRequest.getOrder().getItems());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }


}

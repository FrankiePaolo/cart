package com.purchase.cart.service;

import com.purchase.cart.dto.OrderDTO;
import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.dto.ProductDTO;
import com.purchase.cart.mapper.OrderItemMapper;
import com.purchase.cart.mapper.OrderMapper;
import com.purchase.cart.model.Order;
import com.purchase.cart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    public OrderDTO createOrder(List<OrderItemDTO> itemDTOs) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalVat = BigDecimal.ZERO;

        List<OrderItemDTO> items = new ArrayList<>();

        for (OrderItemDTO itemDTO : itemDTOs) {
            ProductDTO productDTO = productService.getId(itemDTO.getProductId());

            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setProductId(itemDTO.getProductId());
            orderItemDTO.setQuantity(itemDTO.getQuantity());

            BigDecimal price = productDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            BigDecimal vat = price.multiply(productDTO.getVatRate());

            orderItemDTO.setPrice(price);
            orderItemDTO.setVat(vat);

            items.add(orderItemDTO);
            totalPrice = totalPrice.add(price);
            totalVat = totalVat.add(vat);
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderPrice(totalPrice);
        orderDTO.setOrderVat(totalVat);
        orderDTO.setItems(items);

        Order savedOrder = orderRepository.save(orderMapper.toEntity(orderDTO));
        return orderMapper.toDTO(savedOrder);
    }
}
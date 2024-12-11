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
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PriceCalculationService priceCalculationService;

    @Transactional
    public OrderDTO createOrder(List<OrderItemDTO> itemDTOs) {
        List<OrderItemDTO> items = new ArrayList<>();
        List<BigDecimal> prices = new ArrayList<>();
        List<BigDecimal> vatAmounts = new ArrayList<>();

        for (OrderItemDTO itemDTO : itemDTOs) {
            ProductDTO productDTO = productService.getId(itemDTO.getProductId());
            OrderItemDTO orderItemDTO = makeOrderItem(itemDTO);

            BigDecimal price = priceCalculationService.calculateItemPrice(itemDTO, productDTO);
            BigDecimal vat = priceCalculationService.calculateVat(price, productDTO);

            orderItemDTO.setPrice(price);
            orderItemDTO.setVat(vat);

            items.add(orderItemDTO);
            prices.add(price);
            vatAmounts.add(vat);
        }

        BigDecimal totalPrice = priceCalculationService.calculateTotalPrice(prices);
        BigDecimal totalVat = priceCalculationService.calculateTotalPrice(vatAmounts);

        Order savedOrder = makeOrder(totalPrice, totalVat, items);
        return orderMapper.toDTO(savedOrder);
    }

    private static OrderItemDTO makeOrderItem(OrderItemDTO itemDTO) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(itemDTO.getProductId());
        orderItemDTO.setQuantity(itemDTO.getQuantity());
        return orderItemDTO;
    }

    private Order makeOrder(BigDecimal totalPrice, BigDecimal totalVat, List<OrderItemDTO> items) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderPrice(totalPrice);
        orderDTO.setOrderVat(totalVat);
        orderDTO.setItems(items);

        return orderRepository.save(orderMapper.toEntity(orderDTO));
    }
}
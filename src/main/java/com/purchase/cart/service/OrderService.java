package com.purchase.cart.service;

import com.purchase.cart.model.Order;
import com.purchase.cart.model.OrderItem;
import com.purchase.cart.model.Product;
import com.purchase.cart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(List<OrderItem> items) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalVat = BigDecimal.ZERO;

        for (OrderItem item : items) {
            Product product = productService.getProductById(item.getProductId());
            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal vat = price.multiply(product.getVatRate());

            item.setPrice(price);
            item.setVat(vat);

            totalPrice = totalPrice.add(price);
            totalVat = totalVat.add(vat);
        }

        Order order = new Order();
        order.setOrderPrice(totalPrice);
        order.setOrderVat(totalVat);
        order.setItems(items);

        return orderRepository.save(order);
    }
}
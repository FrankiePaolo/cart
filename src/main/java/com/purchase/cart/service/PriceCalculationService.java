package com.purchase.cart.service;


import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

@Service
public class PriceCalculationService {
    private static final int DECIMAL_PLACES = 2;

    @Cacheable(value = "productPrices", key = "#productDTO.productId + '-' + #itemDTO.quantity")
    public BigDecimal calculateItemPrice(OrderItemDTO itemDTO, ProductDTO productDTO) {
        return productDTO.getPrice()
                .multiply(BigDecimal.valueOf(itemDTO.getQuantity()))
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculateVat(BigDecimal price, ProductDTO productDTO) {
        return price.multiply(productDTO.getVatRate())
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculateTotalPrice(List<BigDecimal> prices) {
        return prices.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_EVEN);
    }
}

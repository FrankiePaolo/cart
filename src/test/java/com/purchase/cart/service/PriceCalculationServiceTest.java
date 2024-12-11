package com.purchase.cart.service;


import com.purchase.cart.dto.OrderItemDTO;
import com.purchase.cart.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PriceCalculationServiceTest {
    private PriceCalculationService priceCalculationService;

    @BeforeEach
    void setUp() {
        priceCalculationService = new PriceCalculationService();
    }

    @Test
    void calculateItemPrice_ShouldMultiplyPriceByQuantity() {
        // given
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setQuantity(2);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("10.00"));

        // when
        BigDecimal result = priceCalculationService.calculateItemPrice(itemDTO, productDTO);

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal("20.00"));
    }

    @Test
    void calculateVat_ShouldApplyCorrectVatRate() {
        // given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setVatRate(new BigDecimal("0.20")); // 20% VAT
        BigDecimal price = new BigDecimal("100.00");

        // when
        BigDecimal result = priceCalculationService.calculateVat(price, productDTO);

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal("20.00"));
    }
}
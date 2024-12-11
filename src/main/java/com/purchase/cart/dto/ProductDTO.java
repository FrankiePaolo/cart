package com.purchase.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private int productId;
    private String name;
    private BigDecimal price;
    private BigDecimal vatRate;
}


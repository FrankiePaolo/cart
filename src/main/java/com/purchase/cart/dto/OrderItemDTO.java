package com.purchase.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    @JsonProperty("product_id")
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal vat;
}

package com.purchase.cart.service;

import com.purchase.cart.model.Product;
import com.purchase.cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable("products")
    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }
}
package com.purchase.cart.service;

import com.purchase.cart.dto.ProductDTO;
import com.purchase.cart.mapper.ProductMapper;
import com.purchase.cart.model.Product;
import com.purchase.cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Cacheable("products")
    public ProductDTO getId(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        return productMapper.toDTO(product);
    }
}
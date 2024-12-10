package com.purchase.cart.repository;

import com.purchase.cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Integer> {}
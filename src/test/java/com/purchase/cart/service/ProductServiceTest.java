package com.purchase.cart.service;

import com.purchase.cart.dto.ProductDTO;
import com.purchase.cart.mapper.ProductMapper;
import com.purchase.cart.model.Product;
import com.purchase.cart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, productMapper);
    }

    @Test
    void getId_WithExistingProduct_ReturnsProductDTO() {
        // given
        Product product = new Product();
        product.setProductId(1L);
        product.setPrice(new BigDecimal("10.00"));
        product.setVatRate(new BigDecimal("0.20"));

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setProductId(1);
        expectedDTO.setPrice(new BigDecimal("10.00"));
        expectedDTO.setVatRate(new BigDecimal("0.20"));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(any(Product.class))).thenReturn(expectedDTO);

        // when
        ProductDTO result = productService.getId(1);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualByComparingTo("10.00");
        assertThat(result.getVatRate()).isEqualByComparingTo("0.20");
    }

    @Test
    void getId_WithNonExistingProduct_ThrowsException() {
        // given
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> productService.getId(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product not found with id: 999");
    }
}


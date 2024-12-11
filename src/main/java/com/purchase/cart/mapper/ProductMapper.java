package com.purchase.cart.mapper;

import com.purchase.cart.dto.ProductDTO;
import com.purchase.cart.model.Product;
import org.mapstruct.Mapper;

import java.util.List;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "productId", target = "productId")
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> toDTOList(List<Product> products);
}

package com.waa.marketplace.services;

import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponseDto> getProducts(String name, Double priceMin, Double priceMax, Long categoryId,
                                         Long sellerId, String description, Integer stockAvailable,
                                         Pageable pageable);

    ProductDetailsDto getProductById(Long id);
}

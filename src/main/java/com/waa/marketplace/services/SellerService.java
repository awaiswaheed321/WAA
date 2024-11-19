package com.waa.marketplace.services;

import com.waa.marketplace.dtos.responses.OrderResponseDto;
import com.waa.marketplace.dtos.requests.ProductRequestDto;
import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerService {
    ProductResponseDto createProduct(ProductRequestDto productDto);

    Page<ProductResponseDto> getSellerProducts(String name,
                                               Double priceMin,
                                               Double priceMax,
                                               Long categoryId,
                                               String description,
                                               Boolean active,
                                               Integer stockAvailable,
                                               Pageable pageable);

    ProductDetailsDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productDto);

    void deleteProduct(Long id);

    List<OrderResponseDto> getOrders();

    void updateOrderStatus(Long id, String status);

    void updateProductStock(Long id, int stock);
}

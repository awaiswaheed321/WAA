package com.waa.marketplace.services;

import com.waa.marketplace.dtos.OrderDto;
import com.waa.marketplace.dtos.requests.ProductRequestDto;
import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import com.waa.marketplace.entites.Product;

import java.util.List;

public interface SellerService {
    ProductResponseDto createProduct(ProductRequestDto productDto);

    List<ProductResponseDto> getSellerProducts();

    ProductDetailsDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productDto);

    void deleteProduct(Long id);

    List<OrderDto> getOrders();

    void updateOrderStatus(Long id, String status);

    void updateProductStock(Long id, int stock);
}

package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import com.waa.marketplace.entites.Product;
import com.waa.marketplace.repositories.ProductRepository;
import com.waa.marketplace.services.ProductService;
import com.waa.marketplace.specifications.ProductSpecification;
import com.waa.marketplace.utils.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductResponseDto> getProducts(String name, Double priceMin, Double priceMax, Long categoryId,
                                                Long sellerId, String description,
                                                Integer stockAvailable, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.filter(
                name, priceMin, priceMax, categoryId, sellerId, description, true, stockAvailable);

        return productRepository.findAll(spec, pageable)
                .map(DtoMapper::mapToProductResponseDto);
    }

    @Override
    public ProductDetailsDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not " +
                        "found"));
        return DtoMapper.mapToProductDetailsDto(product);
    }
}

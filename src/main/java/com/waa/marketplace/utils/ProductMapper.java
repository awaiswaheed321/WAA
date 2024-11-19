package com.waa.marketplace.utils;

import com.waa.marketplace.dtos.responses.ReviewResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;
import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.entites.Product;

public class ProductMapper {
    public static ProductDetailsDto mapToProductDetailsDto(Product product) {
        return ProductDetailsDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .seller(new SellerResponseDto(
                        product.getSeller().getId(),
                        product.getSeller().getUser().getName(),
                        product.getSeller().getUser().getEmail()
                ))
                .reviews(product.getReviews().stream()
                        .map(review -> new ReviewResponseDto(
                                review.getId(),
                                product.getName(),
                                review.getRating(),
                                review.getComment()
                        ))
                        .toList()
                )
                .build();
    }
}

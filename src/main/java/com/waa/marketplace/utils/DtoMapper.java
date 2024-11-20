package com.waa.marketplace.utils;

import com.waa.marketplace.dtos.responses.*;
import com.waa.marketplace.entites.*;

public class DtoMapper {
    public static ProductResponseDto mapToProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public static AddressResponseDto mapToAddressResponseDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }

    public static OrderResponseDto mapToOrderResponseDto(Order order) {
        ProductResponseDto productDto = mapToProductResponseDto(order.getProduct());
        return OrderResponseDto.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .status(order.getStatus().name())
                .totalPrice(order.getTotalPrice())
                .product(productDto)
                .build();
    }

    public static SellerResponseDto mapToSellerResponseDto(Seller seller) {
        return SellerResponseDto.builder()
                .id(seller.getId())
                .name(seller.getUser().getName())
                .email(seller.getUser().getEmail())
                .build();
    }

    public static ReviewResponseDto mapToReviewResponseDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .productName(review.getProduct().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }

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

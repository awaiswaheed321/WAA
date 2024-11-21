package com.waa.marketplace.utils;

import com.waa.marketplace.dtos.responses.*;
import com.waa.marketplace.entites.*;

import java.util.List;

public class DtoMapper {
    public static ProductResponseDto mapToProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .images(product.getImages() != null && !product.getImages().isEmpty()
                        ? product.getImages().stream().map(DtoMapper::mapToImageResponseDto).toList()
                        : List.of())
                .build();
    }

    public static ImageResponseDto mapToImageResponseDto(Image image) {
        return ImageResponseDto.builder().id(image.getId()).name(image.getName())
                .imageUrl(image.getImageUrl()).contentType(image.getContentType()).build();
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
                .firstName(seller.getUser().getFirstName()).lastName(seller.getUser().getLastName())
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
                .images(product.getImages() != null && !product.getImages().isEmpty()
                        ? product.getImages().stream().map(DtoMapper::mapToImageResponseDto).toList()
                        : List.of())
                .seller(new SellerResponseDto(
                        product.getSeller().getId(),
                        product.getSeller().getUser().getFirstName(),
                        product.getSeller().getUser().getLastName(),
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

    public static UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail())
                .role(user.getRole()).build();
    }
}

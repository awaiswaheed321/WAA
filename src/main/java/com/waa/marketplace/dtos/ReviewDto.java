package com.waa.marketplace.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for a review, including details of the product, rating, and comment.")
public class ReviewDto {

    @Schema(description = "Unique identifier for the review", example = "123")
    private Long id;

    @Schema(description = "Name of the product being reviewed", example = "Wireless Headphones")
    private String productName;

    @Schema(description = "Rating given by the reviewer (1 to 5)", example = "5")
    private Integer rating;

    @Schema(description = "Comment provided by the reviewer", example = "Excellent sound quality and comfortable design.")
    private String comment;
}

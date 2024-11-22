package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for submitting a review request with a comment and rating")
public class ReviewRequestDto {

    @NotNull
    @Schema(description = "Comment of the review")
    private String comment;

    @NotNull
    @Schema(description = "Rating of the product, from 1 to 5")
    private Integer rating;
}


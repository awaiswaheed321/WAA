package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for creating or updating a category.
 */
@Data
@Builder
public class CategoryRequestDto {

    @Schema(description = "Name of the category", example = "Technology")
    @NotNull
    String name;

    @Schema(description = "Description of the category", example = "A category for all tech-related topics")
    @NotNull
    String description;
}


package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for representing category details in API responses.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    @Schema(description = "Unique identifier for the category", example = "1")
    Long id;

    @Schema(description = "Name of the category", example = "Technology")
    String name;

    @Schema(description = "Description of the category", example = "A category for all tech-related topics")
    String description;
}


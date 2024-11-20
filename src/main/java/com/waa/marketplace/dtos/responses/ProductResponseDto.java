package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data transfer object for Product Response")
public class ProductResponseDto {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "Wireless Mouse")
    private String name;

    @Schema(description = "Description of the product", example = "Ergonomic wireless mouse with long battery life")
    private String description;

    @Schema(description = "Price of the product", example = "29.99")
    private Double price;

    @Schema(description = "Stock quantity of the product", example = "100")
    private int stock;

    @Schema(description = "Category ID of the product", example = "1")
    private long categoryId;

    @Schema(description = "List of DTOs for image associated with a product.")
    List<ImageResponseDto> images;
}

package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for image associated with a product.")
public class ImageResponseDto {
    @Schema(description = "The id of the image.", example = "1")
    Long id;

    @NotNull
    @Schema(description = "The name of the image (e.g., 'product-image.jpg').", example = "product-image.jpg")
    private String name;

    @NotNull
    @Schema(description = "S3 Image URL.")
    private String imageUrl;

    @NotNull
    @Schema(description = "The content type of the image (e.g., 'image/jpeg', 'image/png').", example = "image/jpeg")
    private String contentType;
}
package com.waa.marketplace.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Represents error details returned in API responses.")
public class ErrorDto {

    @Schema(description = "HTTP status code of the error.", example = "404")
    private int statusCode;

    @Schema(description = "Description of the error message.", example = "Resource not found.")
    private String message;
}

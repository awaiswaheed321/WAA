package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import com.waa.marketplace.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Admin APIs", description = "APIs for buyer to view products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Fetch a paginated list of products with optional filters.
     *
     * @param name          Filter products by name (optional).
     * @param priceMin      Minimum price filter (optional).
     * @param priceMax      Maximum price filter (optional).
     * @param categoryId    Filter products by category ID (optional).
     * @param sellerId      Filter products by seller ID (optional).
     * @param description   Filter products by description keywords (optional).
     * @param stockAvailable Filter products by stock availability (optional).
     * @param page          Page number for pagination (default: 0).
     * @param size          Number of products per page (default: 10).
     * @return A paginated list of {@link ProductResponseDto}.
     */
    @Operation(summary = "Retrieve products with optional filters and pagination",
            description = "Fetch a list of products using various optional filters such as name, price range, category ID, seller ID, and more. Supports pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping
    public Page<ProductResponseDto> getProducts(
            @Parameter(description = "Filter products by name", example = "Wireless Mouse")
            @RequestParam(required = false) String name,

            @Parameter(description = "Minimum price filter", example = "10.0")
            @RequestParam(required = false) Double priceMin,

            @Parameter(description = "Maximum price filter", example = "100.0")
            @RequestParam(required = false) Double priceMax,

            @Parameter(description = "Filter products by category ID", example = "1")
            @RequestParam(required = false) Long categoryId,

            @Parameter(description = "Filter products by seller ID", example = "5")
            @RequestParam(required = false) Long sellerId,

            @Parameter(description = "Filter products by description keywords", example = "Ergonomic")
            @RequestParam(required = false) String description,

            @Parameter(description = "Filter products by stock availability", example = "50")
            @RequestParam(required = false) Integer stockAvailable,

            @Parameter(description = "Page number for pagination (default: 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page,

            @Parameter(description = "Number of products per page (default: 10)", example = "10")
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return productService.getProducts(
                name, priceMin, priceMax, categoryId, sellerId, description, stockAvailable, pageable);
    }

    /**
     * Get details of a specific product.
     * This API fetches the details of a product based on its ID.
     *
     * @param id ID of the product.
     * @return Product representing the product details.
     */
    @Operation(summary = "View product details", description = "Fetches " + "details of a specific product by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProductById(@Parameter(description = "ID of the product", example =
            "1") @PathVariable Long id) {
        ProductDetailsDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}

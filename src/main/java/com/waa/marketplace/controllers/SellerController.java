package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.responses.OrderResponseDto;
import com.waa.marketplace.dtos.requests.ProductRequestDto;
import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import com.waa.marketplace.services.SellerService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller")
@Tag(name = "Seller APIs", description = "APIs for managing seller's " + "products, orders, and stock.")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    /**
     * Create a new product.
     * This API allows the seller to create a new product in their inventory.
     *
     * @param productDto Product details to be created.
     * @return Created product details.
     */
    @Operation(summary = "Create a new product", description = "Allows the " + "seller to create a new product.")
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productDto) {
        ProductResponseDto createdProduct = sellerService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    /**
     * Get a paginated list of products listed by the seller with optional filters.
     *
     * @param name           Filter products by name (optional).
     * @param priceMin       Minimum price filter (optional).
     * @param priceMax       Maximum price filter (optional).
     * @param categoryId     Filter products by category ID (optional).
     * @param description    Filter products by description keywords (optional).
     * @param active         Filter by product active status (optional).
     * @param stockAvailable Filter by stock availability (optional).
     * @param page           Page number for pagination (default: 0).
     * @param size           Number of products per page (default: 10).
     * @return A paginated list of {@link ProductResponseDto} representing seller's products.
     */
    @Operation(
            summary = "Retrieve seller's products with optional filters and pagination",
            description = "Fetch a list of products listed by the seller with support for optional filters "
                    + "such as name, price range, category ID, description, active status, and stock availability. "
                    + "Supports pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved seller's products",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDto>> getSellerProducts(
            @Parameter(description = "Filter products by name", example = "Wireless Mouse")
            @RequestParam(required = false) String name,

            @Parameter(description = "Minimum price filter", example = "10.0")
            @RequestParam(required = false) Double priceMin,

            @Parameter(description = "Maximum price filter", example = "100.0")
            @RequestParam(required = false) Double priceMax,

            @Parameter(description = "Filter products by category ID", example = "1")
            @RequestParam(required = false) Long categoryId,

            @Parameter(description = "Filter products by description keywords", example = "Ergonomic")
            @RequestParam(required = false) String description,

            @Parameter(description = "Filter products by active status", example = "true")
            @RequestParam(required = false) Boolean active,

            @Parameter(description = "Filter products by stock availability", example = "50")
            @RequestParam(required = false) Integer stockAvailable,

            @Parameter(description = "Page number for pagination (default: 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page,

            @Parameter(description = "Number of products per page (default: 10)", example = "10")
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDto> products = sellerService.getSellerProducts(name, priceMin, priceMax, categoryId,
                description, active, stockAvailable, pageable);
        return ResponseEntity.ok(products);
    }

    /**
     * Get details of a specific product.
     * This API fetches the details of a product based on its ID.
     *
     * @param id ID of the product.
     * @return Product representing the product details.
     */
    @Operation(summary = "View product details", description = "Fetches " + "details of a specific product by its ID.")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailsDto> getProductById(@Parameter(description = "ID of the product", example =
            "1") @PathVariable Long id) {
        ProductDetailsDto product = sellerService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Update product details.
     * This API allows the seller to update details of an existing product.
     *
     * @param id         ID of the product to update.
     * @param productDto Updated product details.
     * @return Updated ProductDto.
     */
    @Operation(summary = "Update product details", description = "Updates " + "details of an existing product.")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@Parameter(description = "ID of the product to update",
            example = "1") @PathVariable Long id, @RequestBody ProductRequestDto productDto) {
        ProductResponseDto updatedProduct = sellerService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Delete a product.
     * This API allows the seller to delete a product that has not been
     * purchased.
     *
     * @param id ID of the product to delete.
     * @return Success message.
     */
    @Operation(summary = "Delete a product", description = "Deletes a product" + " if it has not been purchased.")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@Parameter(description = "ID of the product to delete",
            example = "1") @PathVariable Long id) {
        sellerService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    /**
     * View all orders.
     * This API fetches all orders placed for the seller's products.
     *
     * @return List of OrderDto objects representing orders.
     */
    @Operation(summary = "View all orders", description = "Fetches all orders" + " placed for the seller's products.")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = sellerService.getOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Update order status.
     * This API allows the seller to update the status of an order (e.g.,
     * Shipped, Delivered).
     *
     * @param id     ID of the order.
     * @param status New status for the order.
     * @return Success message.
     */
    @Operation(summary = "Update order status", description = "Updates the " + "status of an order.")
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@Parameter(description = "ID of the order", example = "1") @PathVariable Long id, @RequestParam String status) {
        sellerService.updateOrderStatus(id, status);
        return ResponseEntity.ok("Order status updated successfully.");
    }

    /**
     * Update product stock.
     * This API allows the seller to update the stock quantity of a product.
     *
     * @param id    ID of the product.
     * @param stock New stock quantity.
     * @return Success message.
     */
    @Operation(summary = "Update product stock", description = "Updates the " + "stock quantity of a product.")
    @PutMapping("/products/{id}/stock")
    public ResponseEntity<String> updateProductStock(@Parameter(description = "ID of the product", example = "1") @PathVariable Long id, @RequestParam int stock) {
        sellerService.updateProductStock(id, stock);
        return ResponseEntity.ok("Product stock updated successfully.");
    }
}

package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.ProductRequestDto;
import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import com.waa.marketplace.entites.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waa.marketplace.dtos.OrderDto;
import com.waa.marketplace.services.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
     * Get a list of seller's products.
     * This API fetches all products listed by the seller.
     *
     * @return List of ProductDto objects representing seller's products.
     */
    @Operation(summary = "Get seller's products", description = "Fetches all " + "products listed by the seller.")
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getSellerProducts() {
        List<ProductResponseDto> products = sellerService.getSellerProducts();
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
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = sellerService.getOrders();
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

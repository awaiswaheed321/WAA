package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.CartItemRequestDto;
import com.waa.marketplace.dtos.responses.CartResponseDto;
import com.waa.marketplace.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing the cart.
 * Provides APIs for adding, updating, and removing items from the buyer's cart.
 */
@RestController
@RequestMapping("/api/v1/buyer/cart")
@Tag(name = "Cart APIs", description = "APIs for managing buyer's cart.")
public class CartController {

    private final CartService cartService;

    /**
     * Constructor for injecting dependencies.
     *
     * @param cartService the service layer for cart-related operations.
     */
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add an item to the cart or update its quantity if it already exists.
     *
     * @param cartItemRequestDto the details of the product to be added or updated.
     * @return a response entity containing the updated cart.
     */
    @PostMapping("/add")
    @Operation(summary = "Add Item to Cart", description = "Adds an item to the buyer's cart or updates the quantity " +
            "if already present.")
    public ResponseEntity<CartResponseDto> addProductToCart(@RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        CartResponseDto cartResponseDto = cartService.addItemToCart(cartItemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponseDto);
    }

    /**
     * Retrieve all items currently in the cart.
     *
     * @return a list of all items in the cart.
     */
    @GetMapping
    @Operation(summary = "Get Cart", description = "Retrieve all items currently in the buyer's cart.")
    public ResponseEntity<CartResponseDto> getCart() {
        CartResponseDto cartResponseDto = cartService.getCart();
        return ResponseEntity.ok(cartResponseDto);
    }

    /**
     * Update the quantity of a specific item in the cart.
     *
     * @param cartItemId the ID of the cart item to update.
     * @param quantity   the new quantity for the item.
     * @return a response entity containing the updated cart.
     */
    @PutMapping("/{cartItemId}/update")
    @Operation(summary = "Update Cart Item", description = "Update the quantity of a specific item in the cart.")
    public ResponseEntity<CartResponseDto> updateCartItem(@PathVariable Long cartItemId,
                                                          @RequestParam Integer quantity) {
        CartResponseDto cartResponseDto = cartService.updateCartItem(cartItemId, quantity);
        return ResponseEntity.ok(cartResponseDto);
    }

    /**
     * Remove an item from the cart.
     *
     * @param cartItemId the ID of the item to remove.
     * @return a response entity indicating the item has been removed.
     */
    @DeleteMapping("/{cartItemId}/remove")
    @Operation(summary = "Remove Item from Cart", description = "Remove a specific item from the cart.")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Clear all items from the cart.
     *
     * @return a response entity indicating the cart has been cleared.
     */
    @DeleteMapping("/clear")
    @Operation(summary = "Clear Cart", description = "Clear all items from the cart.")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}

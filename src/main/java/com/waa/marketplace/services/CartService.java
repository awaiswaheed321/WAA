package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.CartItemRequestDto;
import com.waa.marketplace.dtos.responses.CartResponseDto;

public interface CartService {
    CartResponseDto addItemToCart(CartItemRequestDto cartItemRequestDto);

    CartResponseDto getCart();

    CartResponseDto updateCartItem(Long cartItemId, Integer quantity);

    void removeItemFromCart(Long cartItemId);

    void clearCart();
}

package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.CartItemRequestDto;
import com.waa.marketplace.dtos.responses.CartItemResponseDto;
import com.waa.marketplace.dtos.responses.CartResponseDto;
import com.waa.marketplace.entites.Buyer;
import com.waa.marketplace.entites.Cart;
import com.waa.marketplace.entites.CartItem;
import com.waa.marketplace.entites.Product;
import com.waa.marketplace.repositories.BuyerRepository;
import com.waa.marketplace.repositories.CartItemRepository;
import com.waa.marketplace.repositories.CartRepository;
import com.waa.marketplace.repositories.ProductRepository;
import com.waa.marketplace.services.CartService;
import com.waa.marketplace.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing cart-related operations.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final BuyerRepository buyerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepository,
                           ProductRepository productRepository, BuyerRepository buyerRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.buyerRepository = buyerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public CartResponseDto addItemToCart(CartItemRequestDto cartItemRequestDto) {
        Buyer buyer = getLoggedInBuyer();
        Product product =
                productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(() -> new RuntimeException(
                        "Product not found"));
        Cart cart = buyer.getCart();

        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItemRequestDto.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newCartItem =
                    CartItem.builder().cart(cart).product(product).quantity(cartItemRequestDto.getQuantity()).build();
            cartItemRepository.save(newCartItem);
        }

        List<CartItemResponseDto> cartItems = cart.getItems().stream().map(item -> {
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setId(item.getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPricePerUnit(item.getProduct().getPrice());
            dto.setTotalPrice(item.getQuantity() * item.getProduct().getPrice());
            return dto;
        }).collect(Collectors.toList());

        Double totalPrice = cartItems.stream().mapToDouble(CartItemResponseDto::getTotalPrice).sum();

        CartResponseDto responseDto = new CartResponseDto();
        responseDto.setItems(cartItems);
        responseDto.setTotalPrice(totalPrice);

        return responseDto;
    }

    @Override
    public CartResponseDto getCart() {
        Buyer buyer = getLoggedInBuyer();
        Cart cart = buyer.getCart();

        List<CartItemResponseDto> cartItems = cart.getItems().stream().map(item -> {
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setId(item.getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPricePerUnit(item.getProduct().getPrice());
            dto.setTotalPrice(item.getQuantity() * item.getProduct().getPrice());
            return dto;
        }).collect(Collectors.toList());

        Double totalPrice = cartItems.stream().mapToDouble(CartItemResponseDto::getTotalPrice).sum();

        CartResponseDto responseDto = new CartResponseDto();
        responseDto.setItems(cartItems);
        responseDto.setTotalPrice(totalPrice);

        return responseDto;
    }

    @Override
    @Transactional
    public CartResponseDto updateCartItem(Long cartItemId, Integer quantity) {
        Buyer buyer = getLoggedInBuyer();
        Cart cart = buyer.getCart();
        CartItem cartItem =
                cart.getItems().stream().filter(item -> item.getId().equals(cartItemId)).findFirst()
                        .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return getCart();
    }

@Override
@Transactional
public void removeItemFromCart(Long cartItemId) {
    Buyer buyer = getLoggedInBuyer();
    Cart cart = buyer.getCart();
    CartItem cartItem =
            cart.getItems().stream().filter(item -> item.getId().equals(cartItemId)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));
    cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
    cartItemRepository.delete(cartItem);
    cartRepository.save(cart);
}

    @Override
    @Transactional
    public void clearCart() {
        Buyer buyer = getLoggedInBuyer();
        Cart cart = buyer.getCart();

        cartItemRepository.deleteAll(cart.getItems());
    }

    private Buyer getLoggedInBuyer() {
        Long id = SecurityUtils.getId();
        if (id == null) {
            throw new IllegalStateException("No Buyer ID found for the " + "logged-in user.");
        }
        return buyerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
    }
}

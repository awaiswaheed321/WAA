package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.OrderDto;
import com.waa.marketplace.dtos.ReviewDto;
import com.waa.marketplace.dtos.SellerDto;
import com.waa.marketplace.dtos.requests.ProductRequestDto;
import com.waa.marketplace.dtos.responses.ProductDetailsDto;
import com.waa.marketplace.dtos.responses.ProductResponseDto;
import com.waa.marketplace.entites.Category;
import com.waa.marketplace.entites.Order;
import com.waa.marketplace.entites.Product;
import com.waa.marketplace.entites.Seller;
import com.waa.marketplace.enums.OrderStatus;
import com.waa.marketplace.repositories.CategoryRepository;
import com.waa.marketplace.repositories.OrderRepository;
import com.waa.marketplace.repositories.ProductRepository;
import com.waa.marketplace.repositories.SellerRepository;
import com.waa.marketplace.services.SellerService;
import com.waa.marketplace.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;

    public SellerServiceImpl(ProductRepository productRepository,
                             OrderRepository orderRepository,
                             SellerRepository sellerRepository,
                             CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.sellerRepository = sellerRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        Seller seller = getLoggedInSeller();
        Category category =
                categoryRepository.findById(productDto.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category Not " +
                                "Found"));

        Product product = Product.builder()
                .name(productDto.getName())
                .active(true)
                .category(category)
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .seller(seller)
                .build();

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getCategory().getId()
        );
    }

    @Override
    public List<ProductResponseDto> getSellerProducts() {
        Seller seller = getLoggedInSeller();
        return productRepository.findBySellerId(seller.getId()).stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        product.getCategory().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailsDto getProductById(Long id) {
        Long sellerId = getLoggedInSeller().getId();
        Product product = productRepository.findByIdAndSellerId(id, sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Product not " +
                        "found"));

        return ProductDetailsDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .seller(new SellerDto(
                        product.getSeller().getId(),
                        product.getSeller().getUser().getName(),
                        product.getSeller().getUser().getEmail()
                ))
                .reviews(product.getReviews().stream()
                        .map(review -> new ReviewDto(
                                review.getId(),
                                product.getName(),
                                review.getRating(),
                                review.getComment()
                        ))
                        .toList()
                )
                .build();
    }

    @Override
    public ProductResponseDto updateProduct(Long id,
                                            ProductRequestDto productDto) {
        Long sellerId = getLoggedInSeller().getId();
        Product product =
                productRepository.findByIdAndSellerId(id, sellerId).orElseThrow(() -> new EntityNotFoundException(
                        "Product not found"));
        Category category =
                categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        return new ProductResponseDto(updatedProduct.getId(),
                updatedProduct.getName(), updatedProduct.getDescription(),
                updatedProduct.getPrice(), updatedProduct.getStock(),
                updatedProduct.getCategory().getId());
    }

    @Override
    public void deleteProduct(Long id) {
        Long sellerId = getLoggedInSeller().getId();
        Product product =
                productRepository.findByIdAndSellerId(id, sellerId).orElseThrow(() -> new EntityNotFoundException(
                        "Product not found"));
        productRepository.delete(product);
    }

    @Override
    public void updateProductStock(Long id, int stock) {
        Long sellerId = getLoggedInSeller().getId();
        Product product =
                productRepository.findByIdAndSellerId(id, sellerId).orElseThrow(() -> new EntityNotFoundException(
                        "Product not found"));
        product.setStock(stock);
        productRepository.save(product);
    }

    @Override
    public List<OrderDto> getOrders() {
        Seller seller = getLoggedInSeller();

        return orderRepository.findByProductSellerId(seller.getId()).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getProduct().getName(),
                        order.getQuantity(),
                        order.getStatus().name(),
                        order.getTotalPrice()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        Long sellerId = getLoggedInSeller().getId();
        Order order = orderRepository.findByIdAndProductSellerId(id,
                sellerId).orElseThrow(() -> new EntityNotFoundException(
                "Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
    }

    private Seller getLoggedInSeller() {
        Long id = SecurityUtils.getId();
        if (id == null) {
            throw new IllegalStateException("No seller ID found for the " +
                    "logged-in user.");
        }
        return sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller not found"));
    }
}

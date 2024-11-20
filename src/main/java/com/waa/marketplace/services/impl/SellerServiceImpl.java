package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.ProductRequestDto;
import com.waa.marketplace.dtos.responses.OrderResponseDto;
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
import com.waa.marketplace.specifications.ProductSpecification;
import com.waa.marketplace.utils.DtoMapper;
import com.waa.marketplace.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        return DtoMapper.mapToProductResponseDto(savedProduct);
    }

    @Override
    public Page<ProductResponseDto> getSellerProducts(String name,
                                                      Double priceMin,
                                                      Double priceMax,
                                                      Long categoryId,
                                                      String description,
                                                      Boolean active,
                                                      Integer stockAvailable,
                                                      Pageable pageable) {
        Seller seller = getLoggedInSeller();
        Specification<Product> spec = ProductSpecification.filter(
                name, priceMin, priceMax, categoryId, seller.getId(), description, true, stockAvailable);

        return productRepository.findAll(spec, pageable)
                .map(DtoMapper::mapToProductResponseDto);
    }

    @Override
    public ProductDetailsDto getProductById(Long id) {
        Long sellerId = getLoggedInSeller().getId();
        Product product = productRepository.findByIdAndSellerId(id, sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Product not " +
                        "found"));
        return DtoMapper.mapToProductDetailsDto(product);
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
        return DtoMapper.mapToProductResponseDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Long sellerId = getLoggedInSeller().getId();
        Product product =
                productRepository.findByIdAndSellerId(id, sellerId).orElseThrow(() -> new EntityNotFoundException(
                        "Product not found"));
        List<Order> orders = orderRepository.findByProductId(id);
        if (!orders.isEmpty()) {
            throw new IllegalStateException("Product has orders associated with it");
        }
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
    public List<OrderResponseDto> getOrders() {
        Seller seller = getLoggedInSeller();

        return orderRepository.findByProductSellerId(seller.getId()).stream()
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        new ProductResponseDto(order.getProduct().getId(), order.getProduct().getName(),
                                order.getProduct().getDescription(), order.getProduct().getPrice(),
                                order.getProduct().getStock(), order.getProduct().getCategory().getId()),
                        order.getQuantity(),
                        order.getStatus().name(),
                        order.getTotalPrice()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        Long sellerId = getLoggedInSeller().getId();
        Order order = orderRepository.findByIdAndProductSellerId(id, sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus;

        try {
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        switch (currentStatus) {
            case PENDING:
                if (newStatus == OrderStatus.SHIPPED) {
                    Product product = order.getProduct();
                    int orderQuantity = order.getQuantity();

                    if (product.getStock() < orderQuantity) {
                        throw new IllegalStateException("Insufficient stock to ship the order.");
                    }

                    product.setStock(product.getStock() - orderQuantity);
                    productRepository.save(product);
                } else if (newStatus != OrderStatus.CANCELLED) {
                    throw new IllegalStateException("Order can only be CANCELLED or SHIPPED when PENDING.");
                }
                break;
            case CANCELLED:
                throw new IllegalStateException("Order is CANCELLED and its status cannot be changed.");
            case SHIPPED:
                if (newStatus != OrderStatus.ON_THE_WAY) {
                    throw new IllegalStateException("Order can only be changed to ON_THE_WAY when SHIPPED.");
                }
                break;
            case ON_THE_WAY:
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new IllegalStateException("Order can only be changed to DELIVERED when ON_THE_WAY.");
                }
                break;
            case DELIVERED:
                throw new IllegalStateException("Order is DELIVERED and its status cannot be changed.");
            default:
                throw new IllegalStateException("Unexpected order status: " + currentStatus);
        }

        order.setStatus(newStatus);
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

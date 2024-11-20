package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.OrderRequestDto;
import com.waa.marketplace.dtos.responses.OrderDetailsDto;
import com.waa.marketplace.dtos.responses.OrderResponseDto;
import com.waa.marketplace.entites.Address;
import com.waa.marketplace.entites.Buyer;
import com.waa.marketplace.entites.Order;
import com.waa.marketplace.entites.Product;
import com.waa.marketplace.enums.OrderStatus;
import com.waa.marketplace.repositories.AddressRepository;
import com.waa.marketplace.repositories.BuyerRepository;
import com.waa.marketplace.repositories.OrderRepository;
import com.waa.marketplace.repositories.ProductRepository;
import com.waa.marketplace.services.OrderService;
import com.waa.marketplace.utils.DtoMapper;
import com.waa.marketplace.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.waa.marketplace.utils.DtoMapper.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, AddressRepository addressRepository,
                            BuyerRepository buyerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Buyer buyer = getLoggedInBuyer();
        Product product = productRepository.findById(orderRequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (product.getStock() < orderRequestDto.getQuantity()) {
            throw new IllegalArgumentException("Product stock is less than the requested quantity");
        }

        Address billingAddress = addressRepository.findByIdAndBuyerId(orderRequestDto.getBillingAddressId(),
                        buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Billing Address not found"));
        Address shippingAddress = addressRepository.findByIdAndBuyerId(orderRequestDto.getBillingAddressId(),
                        buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Shipping Address not found"));

        Order order = Order.builder().buyer(buyer).product(product).quantity(orderRequestDto.getQuantity())
                .totalPrice(orderRequestDto.getQuantity() * product.getPrice())
                .status(OrderStatus.PENDING).shippingAddress(shippingAddress).billingAddress(billingAddress).build();

        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        Buyer buyer = getLoggedInBuyer();
        List<Order> orders = orderRepository.findByBuyerId(buyer.getId());
        return orders.stream().map(DtoMapper::mapToOrderResponseDto).toList();
    }

    @Override
    public OrderDetailsDto getOrderById(Long id) {
        Buyer buyer = getLoggedInBuyer();
        Order order = orderRepository.findByIdAndBuyerId(id, buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return OrderDetailsDto.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .status(order.getStatus().name())
                .totalPrice(order.getTotalPrice())
                .product(mapToProductResponseDto(order.getProduct()))
                .billingAddress(mapToAddressResponseDto(order.getBillingAddress()))
                .shippingAddress(mapToAddressResponseDto(order.getShippingAddress()))
                .build();
    }

    @Override
    public void cancelOrder(Long id) {
        Buyer buyer = getLoggedInBuyer();
        Order order = orderRepository.findByIdAndBuyerId(id, buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Order is not in PENDING status");
        }
        orderRepository.delete(order);
    }


    private Buyer getLoggedInBuyer() {
        Long id = SecurityUtils.getId();
        if (id == null) {
            throw new IllegalStateException("No Buyer ID found for the " +
                    "logged-in user.");
        }
        return buyerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
    }
}
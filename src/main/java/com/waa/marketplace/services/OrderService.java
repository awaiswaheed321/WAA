package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.OrderRequestDto;
import com.waa.marketplace.dtos.responses.OrderDetailsDto;
import com.waa.marketplace.dtos.responses.OrderResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(@Valid OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders();

    OrderDetailsDto getOrderById(Long id);

    void cancelOrder(Long id);
}

package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.OrderRequestDto;
import com.waa.marketplace.dtos.responses.OrderDetailsDto;
import com.waa.marketplace.dtos.responses.OrderResponseDto;
import com.waa.marketplace.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing orders.
 * Provides APIs for buyers to create, retrieve, and cancel orders.
 */
@RestController
@RequestMapping("/api/v1/buyer/order")
@Tag(name = "Order APIs", description = "APIs for managing buyer's orders.")
public class OrderController {

    private final OrderService orderService;

    /**
     * Constructor for injecting dependencies.
     *
     * @param orderService the service layer for order-related operations.
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create a new order for the buyer.
     *
     * @param orderRequestDto the details of the order to be created.
     * @return a response entity containing the created order.
     */
    @PostMapping
    @Operation(summary = "Create Order", description = "Allows a buyer to create a new order.")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderResponseDto);
    }

    /**
     * Retrieve all orders placed by the buyer.
     *
     * @return a list of all orders for the buyer.
     */
    @GetMapping
    @Operation(summary = "Get All Orders", description = "Retrieve all orders placed by the buyer.")
    public ResponseEntity<List<OrderResponseDto>> getAllOrder() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Retrieve details of a specific order by its ID.
     *
     * @param id the ID of the order to retrieve.
     * @return a response entity containing the order details.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Order by ID", description = "Retrieve the details of a specific order using its ID.")
    public ResponseEntity<OrderDetailsDto> getOrderById(@PathVariable Long id) {
        OrderDetailsDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Cancel an order by its ID.
     *
     * @param id the ID of the order to cancel.
     * @return a response entity indicating the cancellation.
     */
    @DeleteMapping("/{id}/cancel")
    @Operation(summary = "Cancel Order", description = "Cancel an order by its ID.")
    public ResponseEntity<Void> cancelOrderById(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }
}

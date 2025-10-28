package com.ecommerce.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customer.domain.CustomerId;
import com.ecommerce.order.application.CreateOrderUseCase;
import com.ecommerce.order.application.dto.CreateOrderRequest;
import com.ecommerce.order.application.dto.OrderResponse;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final CreateOrderUseCase createOrderUseCase;
  private final OrderRepository orderRepository;

  public OrderController(CreateOrderUseCase createOrderUseCase,
      OrderRepository orderRepository) {
    this.createOrderUseCase = createOrderUseCase;
    this.orderRepository = orderRepository;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
    try {
      Order order = createOrderUseCase.execute(request);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(OrderResponse.from(order));
    } catch (IllegalArgumentException | IllegalStateException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
    return orderRepository.findById(OrderId.of(orderId))
        .map(order -> ResponseEntity.ok(OrderResponse.from(order)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(@PathVariable String customerId) {
    List<Order> orders = orderRepository.findByCustomerId(CustomerId.of(customerId));
    List<OrderResponse> responses = orders.stream()
        .map(OrderResponse::from)
        .collect(Collectors.toList());
    return ResponseEntity.ok(responses);
  }
}

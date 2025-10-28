package com.ecommerce.order.application;

import com.ecommerce.order.domain.*;
import com.ecommerce.customer.domain.*;
import com.ecommerce.product.domain.*;
import com.ecommerce.order.application.dto.CreateOrderRequest;
import com.ecommerce.shared.infrastructure.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    public CreateOrderUseCase(OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             ProductRepository productRepository,
                             EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order execute(CreateOrderRequest request) {
        // Validate customer exists
        CustomerId customerId = CustomerId.of(request.getCustomerId());
        customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Create order
        Order order = Order.create(customerId);

        // Add items
        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            ProductId productId = ProductId.of(itemRequest.getProductId());
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            if (!product.isAvailable(itemRequest.getQuantity())) {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
            }

            OrderItem item = new OrderItem(
                productId,
                product.getName(),
                product.getPrice(),
                itemRequest.getQuantity()
            );

            order.addItem(item);
            product.reduceStock(itemRequest.getQuantity());
            productRepository.save(product);
        }

        order.confirm();
        Order savedOrder = orderRepository.save(order);

        // Publish domain events
        savedOrder.getDomainEvents().forEach(eventPublisher::publish);
        savedOrder.clearDomainEvents();

        return savedOrder;
    }
}
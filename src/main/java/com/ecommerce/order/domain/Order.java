package com.ecommerce.order.domain;

import com.ecommerce.shared.domain.AggregateRoot;
import com.ecommerce.customer.domain.CustomerId;
import com.ecommerce.product.domain.Money;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Order extends AggregateRoot<OrderId> {
    private CustomerId customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime orderDate;

    private Order(OrderId id, CustomerId customerId) {
        super(id);
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();
    }

    public static Order create(CustomerId customerId) {
        Order order = new Order(OrderId.generate(), customerId);
        order.addDomainEvent(new OrderCreatedEvent(order.getId(), customerId));
        return order;
    }

    public static Order restore(OrderId id, CustomerId customerId, List<OrderItem> items, 
                               OrderStatus status, LocalDateTime orderDate) {
        Order order = new Order(id, customerId);
        order.items = new ArrayList<>(items);
        order.status = status;
        order.orderDate = orderDate;
        return order;
    }

    public void addItem(OrderItem item) {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot modify confirmed order");
        }
        items.add(item);
    }

    public void confirm() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot confirm empty order");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public Money getTotalAmount() {
        return items.stream()
                   .map(OrderItem::getTotalPrice)
                   .reduce(Money.usd(BigDecimal.ZERO), Money::add);
    }

    // Getters
    public CustomerId getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }
}
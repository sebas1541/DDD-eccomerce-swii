package com.ecommerce.order.domain;

import com.ecommerce.shared.domain.DomainEvent;
import com.ecommerce.customer.domain.CustomerId;

public class OrderCreatedEvent extends DomainEvent {
    private final OrderId orderId;
    private final CustomerId customerId;

    public OrderCreatedEvent(OrderId orderId, CustomerId customerId) {
        super();
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }
}
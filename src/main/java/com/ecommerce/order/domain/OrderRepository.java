package com.ecommerce.order.domain;

import com.ecommerce.customer.domain.CustomerId;
import java.util.Optional;
import java.util.List;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    List<Order> findByCustomerId(CustomerId customerId);
}
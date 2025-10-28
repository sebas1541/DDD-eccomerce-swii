package com.ecommerce.order.infrastructure;

import com.ecommerce.order.domain.*;
import com.ecommerce.customer.domain.CustomerId;
import com.ecommerce.product.domain.ProductId;
import com.ecommerce.product.domain.Money;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository jpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity(
            order.getId().getValue(),
            order.getCustomerId().getValue(),
            order.getStatus(),
            order.getOrderDate()
        );

        List<OrderItemEntity> itemEntities = order.getItems().stream()
            .map(item -> new OrderItemEntity(
                entity,
                item.getProductId().getValue(),
                item.getProductName(),
                item.getUnitPrice().getAmount(),
                item.getUnitPrice().getCurrency(),
                item.getQuantity()
            ))
            .collect(Collectors.toList());

        entity.setItems(itemEntities);
        jpaRepository.save(entity);
        return order;
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaRepository.findById(id.getValue())
            .map(this::toDomain);
    }

    @Override
    public List<Order> findByCustomerId(CustomerId customerId) {
        return jpaRepository.findByCustomerId(customerId.getValue()).stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    private Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
            .map(itemEntity -> new OrderItem(
                ProductId.of(itemEntity.getProductId()),
                itemEntity.getProductName(),
                Money.of(itemEntity.getUnitPrice(), itemEntity.getCurrency()),
                itemEntity.getQuantity()
            ))
            .collect(Collectors.toList());

        return Order.restore(
            OrderId.of(entity.getId()),
            CustomerId.of(entity.getCustomerId()),
            items,
            entity.getStatus(),
            entity.getOrderDate()
        );
    }
}
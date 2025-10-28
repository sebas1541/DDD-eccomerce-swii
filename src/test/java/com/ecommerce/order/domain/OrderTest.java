package com.ecommerce.order.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.ecommerce.customer.domain.CustomerId;
import com.ecommerce.product.domain.Money;
import com.ecommerce.product.domain.ProductId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {

  @Test
  void shouldCreateEmptyOrder() {
    CustomerId customerId = CustomerId.generate();
    Order order = Order.create(customerId);

    assertNotNull(order.getId());
    assertEquals(customerId, order.getCustomerId());
    assertEquals(OrderStatus.PENDING, order.getStatus());
    assertTrue(order.getItems().isEmpty());
    assertNotNull(order.getOrderDate());
  }

  @Test
  void shouldAddItemToOrder() {
    Order order = Order.create(CustomerId.generate());
    OrderItem item = new OrderItem(
        ProductId.generate(),
        "Test Product",
        Money.usd(new BigDecimal("99.99")),
        2);

    order.addItem(item);

    assertEquals(1, order.getItems().size());
    assertEquals(item, order.getItems().get(0));
  }

  @Test
  void shouldCalculateTotalAmount() {
    Order order = Order.create(CustomerId.generate());

    OrderItem item1 = new OrderItem(
        ProductId.generate(),
        "Product 1",
        Money.usd(new BigDecimal("99.99")),
        2);

    OrderItem item2 = new OrderItem(
        ProductId.generate(),
        "Product 2",
        Money.usd(new BigDecimal("49.99")),
        1);

    order.addItem(item1);
    order.addItem(item2);

    Money total = order.getTotalAmount();
    assertEquals(new BigDecimal("249.97"), total.getAmount());
  }

  @Test
  void shouldConfirmOrderWithItems() {
    Order order = Order.create(CustomerId.generate());
    OrderItem item = new OrderItem(
        ProductId.generate(),
        "Test Product",
        Money.usd(new BigDecimal("99.99")),
        1);

    order.addItem(item);
    order.confirm();

    assertEquals(OrderStatus.CONFIRMED, order.getStatus());
  }

  @Test
  void shouldNotConfirmEmptyOrder() {
    Order order = Order.create(CustomerId.generate());

    assertThrows(IllegalStateException.class, order::confirm);
  }

  @Test
  void shouldNotAddItemToConfirmedOrder() {
    Order order = Order.create(CustomerId.generate());
    OrderItem item1 = new OrderItem(
        ProductId.generate(),
        "Product 1",
        Money.usd(new BigDecimal("99.99")),
        1);

    order.addItem(item1);
    order.confirm();

    OrderItem item2 = new OrderItem(
        ProductId.generate(),
        "Product 2",
        Money.usd(new BigDecimal("49.99")),
        1);

    assertThrows(IllegalStateException.class, () -> order.addItem(item2));
  }
}

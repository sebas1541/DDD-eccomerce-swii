package com.ecommerce.order.domain;

import com.ecommerce.product.domain.ProductId;
import com.ecommerce.product.domain.Money;
import com.ecommerce.shared.domain.ValueObject;
import java.util.Objects;

public class OrderItem extends ValueObject {
    private final ProductId productId;
    private final String productName;
    private final Money unitPrice;
    private final int quantity;

    public OrderItem(ProductId productId, String productName, Money unitPrice, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Money getTotalPrice() {
        return unitPrice.multiply(quantity);
    }

    // Getters
    public ProductId getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Money getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity &&
               Objects.equals(productId, orderItem.productId) &&
               Objects.equals(productName, orderItem.productName) &&
               Objects.equals(unitPrice, orderItem.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, unitPrice, quantity);
    }
}
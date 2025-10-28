package com.ecommerce.product.domain;

import com.ecommerce.shared.domain.AggregateRoot;

public class Product extends AggregateRoot<ProductId> {
  private String name;
  private String description;
  private Money price;
  private int stockQuantity;

  private Product(ProductId id, String name, String description, Money price, int stockQuantity) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public static Product create(String name, String description, Money price, int stockQuantity) {
    return new Product(ProductId.generate(), name, description, price, stockQuantity);
  }

  public static Product restore(ProductId id, String name, String description, Money price, int stockQuantity) {
    return new Product(id, name, description, price, stockQuantity);
  }

  public boolean isAvailable(int quantity) {
    return stockQuantity >= quantity;
  }

  public void reduceStock(int quantity) {
    if (!isAvailable(quantity)) {
      throw new IllegalStateException("Insufficient stock");
    }
    this.stockQuantity -= quantity;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Money getPrice() {
    return price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }
}

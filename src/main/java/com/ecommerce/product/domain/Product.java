package com.ecommerce.product.domain;

public class Product extends AggregateRoot<ProductId> {
  private String name;
  private String description;
  private Money price;
  private int stockQuantity;

  public Product(ProductId id, String name, String description, Money price, int stockQuantity) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public static Product create(String name, String description, Money price, int stockQuantity) {
    return new Product(ProductId.generate(), name, description, price, stockQuantity);
  }

  public static restore(ProductId id, String name, String description, Money price, int stockQuantity) {
    return new Product(id, name, description, price, stockQuantity);
  }

  public boolean isAvailable(int quantity) {
    return stockQuantity >= quantity;
  }

  public void reduceStock(int quantity) {
    if (quantity > stockQuantity) {
      throw new IllegalArgumentException("Insufficient stock");
    }
    this.stockQuantity -= quantity;
  }

  // Getters
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

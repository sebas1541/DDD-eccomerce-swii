package com.ecommerce.product.infrastructure;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {
  @Id
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal price;

  @Column(nullable = false)
  private String currency;

  @Column(name = "stock_quantity", nullable = false)
  private int stockQuantity;

  // Constructors
  public ProductEntity() {
  }

  public ProductEntity(String id, String name, String description,
      BigDecimal price, String currency, int stockQuantity) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.currency = currency;
    this.stockQuantity = stockQuantity;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }
}

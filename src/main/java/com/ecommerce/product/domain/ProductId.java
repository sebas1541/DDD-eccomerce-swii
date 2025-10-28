package com.ecommerce.product.domain;

public class ProductId extends ValueObject {

  private final String value;

  public ProductId(String value) {
    this.value = value;
  }

  public static ProductId generate() {
    return new ProductId(java.util.UUID.randomUUID().toString());
  }

  public static ProductId of(String value) {
    return new ProductId(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ProductId productId = (ProductId) o;
    return value.equals(productId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

}

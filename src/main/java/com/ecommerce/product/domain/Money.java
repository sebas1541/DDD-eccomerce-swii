package com.ecommerce.product.domain;

import java.math.BigDecimal;

public class Money extends ValueObject {
  private final BigDecimal amount;
  private final String currency;

  public Money(BigDecimal amount, String currency) {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
    this.amount = amount;
    this.currency = currency;
  }

  public static Money of(BigDecimal amount, String currency) {
    return new Money(amount, currency);
  }

  public static Money usd(BigDecimal amount) {
    return new Money(amount, "USD");
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public Money multiply(int quantity) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)), this.currency);
  }

  public Money add(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot add money with different currencies");
    }
    return new Money(this.amount.add(other.amount), this.currency);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Money money = (Money) o;
    return amount.equals(money.amount) && currency.equals(money.currency);
  }

  @Override
  public int hashCode() {
    return Object.hashCode(amount, currency);
  }

}

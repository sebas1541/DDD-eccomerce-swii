package com.ecommerce.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.ecommerce.shared.domain.ValueObject;

public class Money extends ValueObject {
  private final BigDecimal amount;
  private final String currency;

  private Money(BigDecimal amount, String currency) {
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
    return new Money(amount.multiply(BigDecimal.valueOf(quantity)), currency);
  }

  public Money add(Money other) {
    if (!currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot add different currencies");
    }
    return new Money(amount.add(other.amount), currency);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Money money = (Money) o;
    return Objects.equals(amount, money.amount) &&
        Objects.equals(currency, money.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }
}

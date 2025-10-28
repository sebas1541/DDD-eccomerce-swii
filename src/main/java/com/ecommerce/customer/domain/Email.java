package com.ecommerce.customer.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import com.ecommerce.shared.domain.ValueObject;

public class Email extends ValueObject {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

  private final String value;

  private Email(String value) {
    if (!EMAIL_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
    this.value = value;
  }

  public static Email of(String value) {
    return new Email(value);
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
    Email email = (Email) o;
    return Objects.equals(value, email.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}

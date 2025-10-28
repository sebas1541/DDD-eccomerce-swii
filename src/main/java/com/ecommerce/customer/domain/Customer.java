package com.ecommerce.customer.domain;

import com.ecommerce.shared.domain.AggregateRoot;

public class Customer extends AggregateRoot<CustomerId> {
  private String name;
  private Email email;

  private Customer(CustomerId id, String name, Email email) {
    super(id);
    this.name = name;
    this.email = email;
  }

  public static Customer create(String name, Email email) {
    return new Customer(CustomerId.generate(), name, email);
  }

  public static Customer restore(CustomerId id, String name, Email email) {
    return new Customer(id, name, email);
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void updateEmail(Email email) {
    this.email = email;
  }
}

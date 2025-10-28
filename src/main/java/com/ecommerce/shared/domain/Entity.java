package com.ecommerce.shared.domain;

import java.util.Objects;

public abstract class Entity<T> {
  protected T id;

  protected Entity(T id) {
    this.id = id;
  }

  public T getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Entity<?> entity = (Entity<?>) o;
    return id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

package com.ecommerce.customer.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, String> {
  Optional<CustomerEntity> findByEmail(String email);
}

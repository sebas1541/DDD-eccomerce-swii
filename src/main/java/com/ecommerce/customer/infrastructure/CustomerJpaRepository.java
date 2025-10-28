// customer/infrastructure/CustomerJpaRepository.java
package com.ecommerce.customer.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByEmail(String email);
}

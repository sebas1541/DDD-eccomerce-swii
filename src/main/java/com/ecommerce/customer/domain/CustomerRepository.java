// customer/domain/CustomerRepository.java
package com.ecommerce.customer.domain;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(CustomerId id);
    Optional<Customer> findByEmail(Email email);
}

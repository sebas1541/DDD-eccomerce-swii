// customer/infrastructure/CustomerRepositoryImpl.java
package com.ecommerce.customer.infrastructure;

import com.ecommerce.customer.domain.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository jpaRepository;

    public CustomerRepositoryImpl(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = new CustomerEntity(
            customer.getId().getValue(),
            customer.getName(),
            customer.getEmail().getValue()
        );
        jpaRepository.save(entity);
        return customer;
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return jpaRepository.findById(id.getValue())
            .map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
            .map(this::toDomain);
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.restore(
            CustomerId.of(entity.getId()),
            entity.getName(),
            Email.of(entity.getEmail())
        );
    }
}

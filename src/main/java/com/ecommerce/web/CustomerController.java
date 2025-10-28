package com.ecommerce.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customer.domain.Customer;
import com.ecommerce.customer.domain.CustomerId;
import com.ecommerce.customer.domain.CustomerRepository;
import com.ecommerce.customer.domain.Email;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  private final CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @PostMapping
  public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
    try {
      Customer customer = Customer.create(request.getName(), Email.of(request.getEmail()));
      Customer saved = customerRepository.save(customer);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(CustomerResponse.from(saved));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String customerId) {
    return customerRepository.findById(CustomerId.of(customerId))
        .map(customer -> ResponseEntity.ok(CustomerResponse.from(customer)))
        .orElse(ResponseEntity.notFound().build());
  }

  public static class CreateCustomerRequest {
    private String name;
    private String email;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }

  public static class CustomerResponse {
    private String id;
    private String name;
    private String email;

    public static CustomerResponse from(Customer customer) {
      CustomerResponse response = new CustomerResponse();
      response.id = customer.getId().getValue();
      response.name = customer.getName();
      response.email = customer.getEmail().getValue();
      return response;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getEmail() {
      return email;
    }
  }
}

package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

import com.ecommerce.shared.infrastructure.EventPublisher;

@SpringBootApplication
public class ECommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ECommerceApplication.class, args);
  }

  @Bean
  public EventPublisher eventPublisher(ApplicationEventPublisher publisher) {
    return new EventPublisher(publisher);
  }
}

package com.ecommerce.shared.infrastructure;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.ecommerce.shared.domain.DomainEvent;

@Component
public class EventPublisher {
  private final ApplicationEventPublisher publisher;

  public EventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public void publish(DomainEvent event) {
    publisher.publishEvent(event);
  }
}

package com.ecommerce.shared.domain;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    private final LocalDateTime occurredOn;

    protected DomainEvent() {
        this.occurredOn = LocalDateTime.now();
    }

    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}
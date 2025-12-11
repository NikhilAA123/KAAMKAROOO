package com.kaamsetu.infra.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public InMemoryEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }
}

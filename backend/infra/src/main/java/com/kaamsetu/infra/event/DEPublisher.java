package com.kaamsetu.infra.event;

public interface DEPublisher {
    void publish(DomainEvent event);
}

package com.food.ordering.system.domain.event.publisher;

import com.food.ordering.system.domain.event.DoaminEvent;

public interface DomainEventPublisher<T extends DoaminEvent> {
	void publish(T domainEvent);
}
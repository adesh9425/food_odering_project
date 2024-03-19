package com.food.ordering.system.order.service.domain.event;

import java.time.ZonedDateTime;

import com.food.ordering.system.order.service.domain.entity.Order;

public class OrderCreatedEvent extends OrderEvent {

	public OrderCreatedEvent(Order order, ZonedDateTime dateTime) {
		super(order, dateTime);
	}
}
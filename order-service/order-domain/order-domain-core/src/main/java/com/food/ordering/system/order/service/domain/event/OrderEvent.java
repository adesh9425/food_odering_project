package com.food.ordering.system.order.service.domain.event;

import java.time.ZonedDateTime;

import com.food.ordering.system.domain.event.DoaminEvent;
import com.food.ordering.system.order.service.domain.entity.Order;

public abstract class OrderEvent implements DoaminEvent<Order> {
	private final Order order;
	private final ZonedDateTime dateTime;

	public OrderEvent(Order order, ZonedDateTime dateTime) {
		this.order = order;
		this.dateTime = dateTime;
	}

	public Order getOrder() {
		return order;
	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}
}
package com.food.ordering.system.order.service.domain;


import java.util.List;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderDomainService {

	OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant);

	OrderPaidEvent payOrder(Order order);

	void approveOrder(Order order);

	OrderCancelledEvent cancelOrderEvent(Order order, List<String> failureMessages);

	void cancelOrder(Order order, List<String> failureMessages);
}
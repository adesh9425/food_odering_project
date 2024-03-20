package com.food.ordering.system.order.service.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
	@Override
	public OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant) {
		validateResturant(restaurant);
		setOrderProductInformation(order, restaurant);
		order.validateOrder();
		order.initialize();
		log.info("The order with id: {} is initiated ", order.getId()
															 .getValue());
		return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public OrderPaidEvent payOrder(Order order) {
		order.pay();
		log.info("The order  with id: {} is paid", order.getId()
														.getValue());
		return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public void approveOrder(Order order) {
		order.approve();
		log.info("The order with id: {}  is approved", order.getId()
															.getValue());
	}

	@Override
	public OrderCancelledEvent cancelOrderEvent(Order order, List<String> failureMessages) {
		order.initCancelled(failureMessages);
		log.info("The order with id: {} has been cancelled", order.getId()
																  .getValue());
		return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public void cancelOrder(Order order, List<String> failureMessages) {
		order.cancel(failureMessages);
		log.info("The order with id: {} has been cancelled", order.getId()
																  .getValue());

	}

	private void validateResturant(Restaurant restaurant) {
		if (!restaurant.isActive()) {
			throw new OrderDomainException("The restaurant with id: " + restaurant.getId()
																				  .getValue() + " is not active");
		}
	}

	private void setOrderProductInformation(Order order, Restaurant restaurant) {
		order.getItemList()
			 .forEach(item -> {
				 restaurant.getProducts()
						   .forEach(product -> {
							   var currproduct = item.getProduct();
							   if (currproduct.equals(product)) {
								   currproduct.updateWithConfirmedNameAndPrice(product.getName(), product.getPrice());
							   }
						   });
			 });
	}

}
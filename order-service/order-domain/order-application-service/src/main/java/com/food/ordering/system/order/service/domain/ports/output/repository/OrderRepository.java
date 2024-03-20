package com.food.ordering.system.order.service.domain.ports.output.repository;

import java.util.Optional;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.valueobject.TrackingID;

public interface OrderRepository {
	Order save(Order order);
	Optional<Order> findByTrackingId(TrackingID trackingID);
}
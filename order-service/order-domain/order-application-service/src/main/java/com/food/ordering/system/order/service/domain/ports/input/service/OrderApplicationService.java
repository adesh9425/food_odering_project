package com.food.ordering.system.order.service.domain.ports.input.service;

import jakarta.validation.Valid;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuerry;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

public interface OrderApplicationService {

	CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

	TrackOrderResponse trackOrder(@Valid TrackOrderQuerry trackOrderQuerry);
}
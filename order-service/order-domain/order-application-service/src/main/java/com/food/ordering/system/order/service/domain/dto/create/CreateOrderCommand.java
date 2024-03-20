package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {
	@NotNull
	private final UUID customerId;
	@NotNull
	private final UUID restaurantId;
	@NotNull
	private final BigDecimal price;
	@NotNull
	private final List<OrderItem> orderItems;
	@NotNull
	private final OrderAddress address;

}
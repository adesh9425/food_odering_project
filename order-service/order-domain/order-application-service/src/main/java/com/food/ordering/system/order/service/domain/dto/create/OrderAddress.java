package com.food.ordering.system.order.service.domain.dto.create;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderAddress {
	@NotNull
	@Max(value = 20)
	private final String street;
	@NotNull
	@Max(value = 10)
	private final String postalCode;
	@NotNull
	@Max(value = 20)
	private final String city;
}
package com.food.ordering.system.order.service.domain.valueobject;

import java.util.UUID;

import com.food.ordering.system.domain.valueobject.BaseID;

public class TrackingID extends BaseID<UUID> {
	public TrackingID(UUID value) {
		super(value);
	}
}
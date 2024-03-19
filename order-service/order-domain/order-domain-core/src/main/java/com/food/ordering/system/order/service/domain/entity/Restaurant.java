package com.food.ordering.system.order.service.domain.entity;

import java.util.List;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.RestaurantID;

public class Restaurant extends AggregateRoot<RestaurantID> {

	private boolean isActive;
	private final List<Product> products;

	private Restaurant(Builder builder) {
		setId(builder.restaurantID);
		isActive = builder.isActive;
		products = builder.products;
	}

	public boolean isActive() {
		return isActive;
	}

	public List<Product> getProducts() {
		return products;
	}

	public static final class Builder {
		private RestaurantID restaurantID;
		private boolean isActive;
		private List<Product> products;

		private Builder() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public Builder id(RestaurantID val) {
			restaurantID = val;
			return this;
		}

		public Builder isActive(boolean val) {
			isActive = val;
			return this;
		}

		public Builder products(List<Product> val) {
			products = val;
			return this;
		}

		public Restaurant build() {
			return new Restaurant(this);
		}
	}
}
package com.food.ordering.system.order.service.domain.entity;

import java.util.List;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.CustomerID;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderID;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.domain.valueobject.RestaurantID;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingID;

public class Order extends AggregateRoot<OrderID> {
	private final CustomerID customerID;
	private final RestaurantID restaurantID;
	private final StreetAddress deleviryAddress;
	private final Money price;
	private final List<OrderItem> itemList;
	private TrackingID trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessages;

	private Order(Builder builder) {
		super.setId(builder.orderID);
		customerID = builder.customerID;
		restaurantID = builder.restaurantID;
		deleviryAddress = builder.deleviryAddress;
		price = builder.price;
		itemList = builder.itemList;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		failureMessages = builder.failureMessages;
	}

	public CustomerID getCustomerID() {
		return customerID;
	}

	public RestaurantID getRestaurantID() {
		return restaurantID;
	}

	public StreetAddress getDeleviryAddress() {
		return deleviryAddress;
	}

	public Money getPrice() {
		return price;
	}

	public List<OrderItem> getItemList() {
		return itemList;
	}

	public TrackingID getTrackingId() {
		return trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

	public static final class Builder {
		private OrderID orderID;
		private CustomerID customerID;
		private RestaurantID restaurantID;
		private StreetAddress deleviryAddress;
		private Money price;
		private List<OrderItem> itemList;
		private TrackingID trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessages;

		private Builder() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public Builder id(OrderID val) {
			orderID = val;
			return this;
		}

		public Builder customerID(CustomerID val) {
			customerID = val;
			return this;
		}

		public Builder restaurantID(RestaurantID val) {
			restaurantID = val;
			return this;
		}

		public Builder deleviryAddress(StreetAddress val) {
			deleviryAddress = val;
			return this;
		}

		public Builder price(Money val) {
			price = val;
			return this;
		}

		public Builder itemList(List<OrderItem> val) {
			itemList = val;
			return this;
		}

		public Builder trackingId(TrackingID val) {
			trackingId = val;
			return this;
		}

		public Builder orderStatus(OrderStatus val) {
			orderStatus = val;
			return this;
		}

		public Builder failureMessages(List<String> val) {
			failureMessages = val;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}
}
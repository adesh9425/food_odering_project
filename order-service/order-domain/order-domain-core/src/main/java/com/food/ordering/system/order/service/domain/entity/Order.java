package com.food.ordering.system.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.CustomerID;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderID;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.domain.valueobject.RestaurantID;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemID;
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
		deleviryAddress = builder.deliveryAddress;
		price = builder.price;
		itemList = builder.itemList;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		failureMessages = builder.failureMessages;
	}

	public void pay() {
		if (orderStatus != OrderStatus.PENDING) {
			throw new OrderDomainException("The order is not in correct status for payment operation");
		}
		orderStatus = OrderStatus.PAID;
	}

	public void approve() {
		if (orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("The order is not in correct status for approve operation");
		}
		orderStatus = OrderStatus.APPROVED;
	}

	public void initCancelled(List<String> failureMessage) {
		if (orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("The order is not in correct status for init cancel operation");
		}
		orderStatus = OrderStatus.CANCELLING;
		updateFailureMessage(failureMessage);
	}

	public void cancel(List<String> failureMessage) {
		if ((orderStatus == OrderStatus.CANCELLING) || (orderStatus == OrderStatus.PENDING)) {
			throw new OrderDomainException("The order is not in correct status for  cancel operation");
		}
		orderStatus = OrderStatus.CANCELLED;
		updateFailureMessage(failureMessage);
	}

	private void updateFailureMessage(List<String> failureMessage) {
		if ((failureMessages != null) && (failureMessage != null)) {
			failureMessages.addAll(failureMessage.stream()
												 .filter(msg -> !msg.isEmpty())
												 .toList());
		}
		if (failureMessages == null) {
			failureMessages = failureMessage;
		}
	}

	public void validateOrder() {
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();
	}

	private void validateInitialOrder() {
		if ((orderStatus != null) && (getId() != null)) {
			throw new OrderDomainException("Order is not in correct state for initialization!");
		}
	}

	private void validateTotalPrice() {
		if ((price == null) || !price.isGreaterThanZero()) {
			throw new OrderDomainException("Total price is not in correct start for initialization!");
		}
	}

	private void validateItemsPrice() {
		var orderItemTotal = itemList.stream()
									 .map(item -> {
										 validateItemPrice(item);
										 return item.getSubTotal();
									 })
									 .reduce(Money.ZERO, Money::add);
		if (!price.equals(orderItemTotal)) {
			throw new OrderDomainException("Total price: " + price.getAmount() + " is not equal to subtotal of items: " + orderItemTotal.getAmount() + " !");
		}

	}

	private void validateItemPrice(OrderItem item) {
		if (!item.isPriceValid()) {
			throw new OrderDomainException("Order item price: " + item.getPrice()
																	  .getAmount() + " is not valid for the product" + item.getProduct()
																														   .getId()
																														   .getValue() + " !");
		}
	}

	public void initialize() {
		setId(new OrderID(UUID.randomUUID()));
		trackingId = new TrackingID(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();
	}

	private void initializeOrderItems() {
		long itemId = 1;
		for (OrderItem orderItem : itemList) {
			orderItem.initializeOrderItem(getId(), new OrderItemID(itemId));
			itemId++;
		}
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
		private StreetAddress deliveryAddress;
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

		public Builder deliveryAddress(StreetAddress val) {
			deliveryAddress = val;
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
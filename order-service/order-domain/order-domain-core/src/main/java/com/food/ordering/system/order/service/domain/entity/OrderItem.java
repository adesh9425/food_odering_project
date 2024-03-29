package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderID;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemID;

public class OrderItem extends BaseEntity<OrderItemID> {

	private final Product product;
	private final int quantity;
	private final Money price;
	private final Money subTotal;
	private OrderID orderID;

	private OrderItem(Builder builder) {
		super.setId(builder.orderItemID);
		product = builder.product;
		quantity = builder.quantity;
		price = builder.price;
		subTotal = builder.subTotal;
	}

	boolean isPriceValid() {
		return price.isGreaterThanZero() && price.equals(product.getPrice()) && price.multiply(quantity)
																					 .equals(subTotal);
	}

	public void initializeOrderItem(OrderID orderid, OrderItemID orderItemID) {
		setId(orderItemID);
		this.orderID = orderid;

	}

	public OrderID getOrderID() {
		return orderID;
	}

	public void setOrderID(OrderID orderID) {
		this.orderID = orderID;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getPrice() {
		return price;
	}

	public Money getSubTotal() {
		return subTotal;
	}

	public static final class Builder {
		private OrderItemID orderItemID;
		private Product product;
		private int quantity;
		private Money price;
		private Money subTotal;

		private Builder() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public Builder id(OrderItemID val) {
			orderItemID = val;
			return this;
		}

		public Builder product(Product val) {
			product = val;
			return this;
		}

		public Builder quantity(int val) {
			quantity = val;
			return this;
		}

		public Builder price(Money val) {
			price = val;
			return this;
		}

		public Builder subTotal(Money val) {
			subTotal = val;
			return this;
		}

		public OrderItem build() {
			return new OrderItem(this);
		}
	}
}
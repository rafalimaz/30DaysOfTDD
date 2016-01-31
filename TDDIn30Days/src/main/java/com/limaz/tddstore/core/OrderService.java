package com.limaz.tddstore.core;

import java.util.UUID;

public class OrderService {
	private IOrderDataService orderDataService;

	public OrderService(IOrderDataService orderDataService) {
		this.orderDataService = orderDataService;
	}

	public UUID placeOrder(UUID customerId, IShoppingCart shoppingCart) {
		Order order = new Order();
		UUID orderId = save(order);
		return orderId;
	}

	private UUID save(Order order) {
		return orderDataService.save(order);
	}
}

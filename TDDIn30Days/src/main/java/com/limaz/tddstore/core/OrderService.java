package com.limaz.tddstore.core;

import java.util.UUID;

public class OrderService {
	private IOrderDataService orderDataService;

	public OrderService(IOrderDataService orderDataService) {
		this.orderDataService = orderDataService;
	}

	public UUID placeOrder(UUID customerId, IShoppingCart shoppingCart) throws InvalidOrderException {
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			if (item.quantity == 0) {
				throw new InvalidOrderException();
			}
		}
		
		Order order = new Order();
		return save(order);
	}

	private UUID save(Order order) {
		return orderDataService.save(order);
	}
}

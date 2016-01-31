package com.limaz.tddstore.core;

import java.util.UUID;

public class OrderService {
	private IOrderDataService orderDataService;
	private ICustomerService customerService;

	public OrderService(IOrderDataService orderDataService, ICustomerService customerService) {
		this.orderDataService = orderDataService;
		this.customerService = customerService;
	}

	public UUID placeOrder(UUID customerId, IShoppingCart shoppingCart) throws InvalidOrderException {
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			if (item.quantity == 0) {
				throw new InvalidOrderException();
			}
		}
		
		Customer customer = customerService.getCustomer(customerId);
		Order order = new Order();
		return save(order);
	}

	private UUID save(Order order) {
		return orderDataService.save(order);
	}
}

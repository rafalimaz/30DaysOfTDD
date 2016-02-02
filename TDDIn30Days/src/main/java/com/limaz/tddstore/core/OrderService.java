package com.limaz.tddstore.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderService {
	private static final String USERNAME = "Bob";
	private static final String PASSWORD = "Foo";
	private IOrderDataService orderDataService;
	private ICustomerService customerService;
	private IOrderFulfillmentService orderFullFillmentService;

	public OrderService(IOrderDataService orderDataService, ICustomerService customerService, IOrderFulfillmentService orderFulfillmentService) {
		this.orderDataService = orderDataService;
		this.customerService = customerService;
		this.orderFullFillmentService = orderFulfillmentService;
	}

	public UUID placeOrder(UUID customerId, IShoppingCart shoppingCart) throws InvalidOrderException {
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			if (item.quantity == 0) {
				throw new InvalidOrderException();
			}
		}
		
		
		placeOrderWithFullfillmentService(customerId, shoppingCart);
		
		Order order = new Order();
		return save(order);
	}

	private void placeOrderWithFullfillmentService(UUID customerId, IShoppingCart shoppingCart) {
		Customer customer = customerService.getCustomer(customerId);
		//customerService has no implementation
		customer = new Customer(customerId, "Foo", "Bar");
		UUID orderFullfillmentSessionId = openOrderFullfillmentService();
		
		placeOrderWithFullfillmentService(orderFullfillmentSessionId, shoppingCart, customer);
		
		closeOrderFullfillmentService(orderFullfillmentSessionId);
	}

	private void placeOrderWithFullfillmentService(UUID orderFullfillmentSessionId, IShoppingCart shoppingCart, Customer customer) {
		Map<UUID, Integer> items = checkInventoryLevels(orderFullfillmentSessionId, shoppingCart);
		boolean orderPlaced =  orderFullFillmentService.placeOrder(orderFullfillmentSessionId, items, customer.firstName);
	}

	private Map<UUID, Integer> checkInventoryLevels(UUID orderFullfillmentSessionId, IShoppingCart shoppingCart) {
		Map<UUID, Integer> items = new HashMap<UUID, Integer>();
		for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
			UUID itemId = shoppingCartItem.itemId;
			int itemQuantity = shoppingCartItem.quantity;
			
			boolean itemsIsInInventory = orderFullFillmentService.isInInventory(orderFullfillmentSessionId, itemId, itemQuantity);
			items.put(itemId, itemQuantity);
		}
		return items;
	}

	private void closeOrderFullfillmentService(UUID orderFullfillmentSessionId) {
		orderFullFillmentService.closeSession(orderFullfillmentSessionId);
	}

	private UUID openOrderFullfillmentService() {
		return orderFullFillmentService.openSession(USERNAME, PASSWORD);
	}

	private UUID save(Order order) {
		return orderDataService.save(order);
	}
}

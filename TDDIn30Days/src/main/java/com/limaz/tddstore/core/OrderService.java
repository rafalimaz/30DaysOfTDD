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
		
		Customer customer = customerService.getCustomer(customerId);
		//customerService has no implementation
		customer = new Customer(customerId, "Foo", "Bar");
		UUID orderFullfillmentSessionId = orderFullFillmentService.openSession(USERNAME, PASSWORD);
		UUID firstItemId = shoppingCart.getItems().get(0).itemId;
		int firstItemQuantity = shoppingCart.getItems().get(0).quantity;
		
		//Check Inventory Level
		boolean itemsIsInInventory = orderFullFillmentService.isInInventory(orderFullfillmentSessionId, firstItemId, firstItemQuantity);
		
		//Place Order
		Map<UUID, Integer> items = new HashMap<UUID, Integer>();
		items.put(firstItemId, firstItemQuantity);
		boolean orderPlaced =  orderFullFillmentService.placeOrder(orderFullfillmentSessionId, items, customer.firstName);
		
		//Close Session
		orderFullFillmentService.closeSession(orderFullfillmentSessionId);
		
		Order order = new Order();
		return save(order);
	}

	private UUID save(Order order) {
		return orderDataService.save(order);
	}
}

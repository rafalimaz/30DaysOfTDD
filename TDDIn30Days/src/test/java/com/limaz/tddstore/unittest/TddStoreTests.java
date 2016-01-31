package com.limaz.tddstore.unittest;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.mockito.Mockito;

import com.limaz.tddstore.core.IOrderDataService;
import com.limaz.tddstore.core.IShoppingCart;
import com.limaz.tddstore.core.Order;
import com.limaz.tddstore.core.OrderService;
import com.limaz.tddstore.core.ShoppingCart;
import com.limaz.tddstore.core.ShoppingCartItem;

public class TddStoreTests {
	
	@Test
	public void returnOrderIdIfItemsAmountIsCorrect() throws Exception {
		IShoppingCart shoppingCart = new ShoppingCart();
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(UUID.randomUUID(), 1);
		shoppingCart.getItems().add(shoppingCartItem);
		
		UUID customerId = UUID.randomUUID();
		UUID expectedOrderId = UUID.randomUUID();
		
		IOrderDataService orderDataService = Mockito.mock(IOrderDataService.class);
		Mockito.stub(orderDataService.save(Mockito.any(Order.class))).toReturn(expectedOrderId);
		
		OrderService orderService = new OrderService(orderDataService);
		Object expectedResult = orderService.placeOrder(customerId, shoppingCart);
		
		assertEquals(expectedOrderId, expectedResult);		
		Mockito.verify(orderDataService, Mockito.times(1)).save(Mockito.any(Order.class));
	}
}


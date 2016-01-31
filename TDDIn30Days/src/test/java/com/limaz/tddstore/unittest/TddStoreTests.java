package com.limaz.tddstore.unittest;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.limaz.tddstore.core.IOrderDataService;
import com.limaz.tddstore.core.IShoppingCart;
import com.limaz.tddstore.core.InvalidOrderException;
import com.limaz.tddstore.core.Order;
import com.limaz.tddstore.core.OrderService;
import com.limaz.tddstore.core.ShoppingCart;
import com.limaz.tddstore.core.ShoppingCartItem;

public class TddStoreTests {	
	private OrderService orderService;
	private IOrderDataService orderDataService;
	
	@Before
	public void setUp() {
		orderDataService = Mockito.mock(IOrderDataService.class);
		orderService = new OrderService(orderDataService);		
	}
	
	@Test
	public void whenUserPlacesACorrectOrderThenAnOrderNumberShouldBeReturned() throws Exception {
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
	
	@Test (expected = InvalidOrderException.class)	
	public void whenAUserAttemptsToOrderAnItemWithAQuantityOfZeroThrowInvalidOrderException() throws Exception{
		ShoppingCart shoppingCart = new ShoppingCart();
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(UUID.randomUUID(), 0);
		shoppingCart.getItems().add(shoppingCartItem);
		
		UUID customerId = UUID.randomUUID();
		UUID expectedOrderId = UUID.randomUUID();
				
		Mockito.stub(orderDataService.save(Mockito.any(Order.class))).toReturn(expectedOrderId);
		
		orderService.placeOrder(customerId, shoppingCart);		
	}
}


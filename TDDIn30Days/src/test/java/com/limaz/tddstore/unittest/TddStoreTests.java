package com.limaz.tddstore.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.limaz.tddstore.core.Customer;
import com.limaz.tddstore.core.ICustomerService;
import com.limaz.tddstore.core.IOrderDataService;
import com.limaz.tddstore.core.IOrderFulfillmentService;
import com.limaz.tddstore.core.InvalidOrderException;
import com.limaz.tddstore.core.Order;
import com.limaz.tddstore.core.OrderService;
import com.limaz.tddstore.core.ShoppingCart;
import com.limaz.tddstore.core.ShoppingCartItem;

public class TddStoreTests {	
	private OrderService orderService;
	private IOrderDataService orderDataService;
	private ICustomerService customerService;
	private IOrderFulfillmentService orderFullfillmentService;
	
	@Before
	public void setUp() {
		orderDataService = Mockito.mock(IOrderDataService.class);
		customerService = Mockito.mock(ICustomerService.class);		
		orderFullfillmentService = Mockito.mock(IOrderFulfillmentService.class);
		orderService = new OrderService(orderDataService, customerService, orderFullfillmentService);
	}
	
	@Test
	public void whenUserPlacesACorrectOrderThenAnOrderNumberShouldBeReturned() throws Exception {
		//Arrange
		
		UUID itemId = UUID.randomUUID();
		ShoppingCart shoppingCart = new ShoppingCart();		
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(itemId, 1);
		shoppingCart.getItems().add(shoppingCartItem);
		
		UUID customerId = UUID.randomUUID();
		UUID expectedOrderId = UUID.randomUUID();
		UUID orderFullfillmentSesssionID = UUID.randomUUID();
		
		Customer customerToReturn = new Customer(customerId, "Fred", "Flinstone");
		
		Mockito.stub(orderDataService.save(Mockito.any(Order.class))).toReturn(expectedOrderId);
		Mockito.stub(customerService.getCustomer(customerId)).toReturn(customerToReturn);
		Mockito.stub(orderFullfillmentService.openSession(Mockito.any(String.class), Mockito.any(String.class))).toReturn(orderFullfillmentSesssionID);
		Mockito.stub(orderFullfillmentService.isInInventory(orderFullfillmentSesssionID, itemId, 1)).toReturn(true);
		
		//To make work with Map need to add a Matcher. But Matcher only work if all parameters are Matchers
		Mockito.stub(orderFullfillmentService.placeOrder(Matchers.eq(orderFullfillmentSesssionID), Matchers.<Map<UUID, Integer>>any(), Matchers.anyString())).toReturn(true);
		
		//To make work with void method closeSession just do nothing
		Mockito.doNothing().when(orderFullfillmentService).closeSession(orderFullfillmentSesssionID);
		
		//Act
		UUID result = orderService.placeOrder(customerId, shoppingCart);
		
		//Assert
		assertEquals(expectedOrderId, result);
		Mockito.verify(orderDataService, Mockito.times(1)).save(Mockito.any(Order.class));
		Mockito.verify(orderFullfillmentService, Mockito.times(1)).isInInventory(orderFullfillmentSesssionID, itemId, 1);
	}
	
	@Test
	public void whenAUserAttemptsToOrderAnItemWithAQuantityOfZeroThrowInvalidOrderException() throws Exception{
		ShoppingCart shoppingCart = new ShoppingCart();
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(UUID.randomUUID(), 0);
		shoppingCart.getItems().add(shoppingCartItem);
		
		UUID customerId = UUID.randomUUID();
		UUID expectedOrderId = UUID.randomUUID();
				
		Mockito.stub(orderDataService.save(Mockito.any(Order.class))).toReturn(expectedOrderId);
		try {
		    orderService.placeOrder(customerId, shoppingCart);
		} catch (InvalidOrderException e) {
			Mockito.verify(orderDataService, Mockito.times(0)).save(Mockito.any(Order.class));
			return;			
		}
		
		fail();	
	}
	
	@Test
	public void whenAValidCustomerPlacesAValidOrderAnTheOrderServiceSholdBeAbleToGetACustomerFromTheCustomerService() throws Exception {
		ShoppingCart shoppingCart = new ShoppingCart();
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(UUID.randomUUID(), 1);
		shoppingCart.getItems().add(shoppingCartItem);
		
		UUID customerId = UUID.randomUUID();
		Customer customerToReturn = new Customer(customerId, "Fred", "Flinstone");
		
		Mockito.stub(customerService.getCustomer(customerId)).toReturn(customerToReturn);
		
		orderService.placeOrder(customerId, shoppingCart);
		Mockito.verify(customerService, Mockito.times(1)).getCustomer(Mockito.any(UUID.class));
	}
	
	@Test
	public void whenUserPlacesOrderWithItemThatIsInInventoryOrderFulfillmentWorkflowShouldComplete() throws InvalidOrderException {
		//Arrange
		ShoppingCart shoppingCart = new ShoppingCart();
		UUID itemId = UUID.randomUUID();
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(itemId, 1);
		shoppingCart.getItems().add(shoppingCartItem);
		
		UUID customerId = UUID.randomUUID();
		Customer customerToReturn = new Customer(customerId, "Fred", "Flinstone");
		
		UUID orderFullfillmentSesssionID = UUID.randomUUID();
		
		Mockito.stub(customerService.getCustomer(customerId)).toReturn(customerToReturn);
		Mockito.stub(orderFullfillmentService.openSession(Mockito.any(String.class), Mockito.any(String.class))).toReturn(orderFullfillmentSesssionID);
		Mockito.stub(orderFullfillmentService.isInInventory(orderFullfillmentSesssionID, itemId, 1)).toReturn(true);
		
		//To make work with Map need to add a Matcher. But Matcher only work if all parameters are Matchers
		Mockito.stub(orderFullfillmentService.placeOrder(Matchers.eq(orderFullfillmentSesssionID), Matchers.<Map<UUID, Integer>>any(), Matchers.anyString())).toReturn(true);
		
		//To make work with void method closeSession just do nothing
		Mockito.doNothing().when(orderFullfillmentService).closeSession(orderFullfillmentSesssionID);
		
		//Act
		orderService.placeOrder(customerId, shoppingCart);
		
		//Assert
		InOrder inOrder = Mockito.inOrder(orderFullfillmentService);
		inOrder.verify(orderFullfillmentService).openSession(Mockito.any(String.class), Mockito.any(String.class));
		inOrder.verify(orderFullfillmentService).isInInventory(orderFullfillmentSesssionID, itemId, 1);
		inOrder.verify(orderFullfillmentService).placeOrder(Matchers.eq(orderFullfillmentSesssionID), Matchers.<Map<UUID, Integer>>any(), Matchers.anyString());
		inOrder.verify(orderFullfillmentService).closeSession(orderFullfillmentSesssionID);
	}
	
	@Test
	public void whenUserPlacesACorrectOrderWithMoreThenOneItemThenAnOrderNumberShouldBeReturned() throws InvalidOrderException {
		//Arrange
		ShoppingCart shoppingCart = new ShoppingCart();
		UUID itemOneId = UUID.randomUUID();
		UUID itemTwoId = UUID.randomUUID();
		int itemOneQuantity = 1;
		int itemTwoQuantity = 4;
		
		shoppingCart.getItems().add(new ShoppingCartItem(itemOneId, itemOneQuantity));
		shoppingCart.getItems().add(new ShoppingCartItem(itemTwoId, itemTwoQuantity));
		
		UUID customerId = UUID.randomUUID();
		UUID expectedOrderId = UUID.randomUUID();
		UUID orderFullfillmentSesssionID = UUID.randomUUID();		
		Customer customerToReturn = new Customer(customerId, "Fred", "Flinstone");
		
		Mockito.stub(orderDataService.save(Mockito.any(Order.class))).toReturn(expectedOrderId);		
		Mockito.stub(customerService.getCustomer(customerId)).toReturn(customerToReturn);
		Mockito.stub(orderFullfillmentService.openSession(Mockito.any(String.class), Mockito.any(String.class))).toReturn(orderFullfillmentSesssionID);
		Mockito.stub(orderFullfillmentService.isInInventory(orderFullfillmentSesssionID, itemOneId, itemOneQuantity)).toReturn(true);
		Mockito.stub(orderFullfillmentService.isInInventory(orderFullfillmentSesssionID, itemTwoId, itemTwoQuantity)).toReturn(true);
		
		//To make work with Map need to add a Matcher. But Matcher only work if all parameters are Matchers
		Mockito.stub(orderFullfillmentService.placeOrder(Matchers.eq(orderFullfillmentSesssionID), Matchers.<Map<UUID, Integer>>any(), Matchers.anyString())).toReturn(true);
		
		//To make work with void method closeSession just do nothing
		Mockito.doNothing().when(orderFullfillmentService).closeSession(orderFullfillmentSesssionID);
		
		//Act
		UUID result = orderService.placeOrder(customerId, shoppingCart);
		
		//Assert
		assertEquals(expectedOrderId, result);
		
		Mockito.verify(orderDataService, Mockito.times(1)).save(Mockito.any(Order.class));
		
		Mockito.verify(orderFullfillmentService, Mockito.times(1)).openSession(Mockito.any(String.class), Mockito.any(String.class));
		Mockito.verify(orderFullfillmentService, Mockito.times(2)).isInInventory(Mockito.eq(orderFullfillmentSesssionID), Mockito.any(UUID.class), Mockito.any(Integer.class));
		Mockito.verify(orderFullfillmentService, Mockito.times(1)).placeOrder(Matchers.eq(orderFullfillmentSesssionID), Matchers.<Map<UUID, Integer>>any(), Matchers.anyString());
		Mockito.verify(orderFullfillmentService, Mockito.times(1)).closeSession(orderFullfillmentSesssionID);
	}
}


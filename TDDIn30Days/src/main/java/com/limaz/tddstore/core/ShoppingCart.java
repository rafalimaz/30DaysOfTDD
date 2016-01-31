package com.limaz.tddstore.core;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements IShoppingCart {
	public List<ShoppingCartItem> items;
	
	public ShoppingCart() {		
		items = new ArrayList<ShoppingCartItem>();
	}

	public List<ShoppingCartItem> getItems() {
		return items;
	}
}

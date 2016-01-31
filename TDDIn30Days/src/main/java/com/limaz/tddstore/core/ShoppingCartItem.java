package com.limaz.tddstore.core;

import java.util.UUID;

public class ShoppingCartItem
{
    public UUID itemId;
    public int quantity;
    
	public ShoppingCartItem(UUID itemId, int quantity) {		
		this.itemId = itemId;
		this.quantity = quantity;
	}
}
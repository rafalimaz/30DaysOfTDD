package com.limaz.tddstore.core;

import java.util.Map;
import java.util.UUID;

public interface IOrderFulfillmentService {
	public UUID openSession(String user, String password);

	public boolean isInInventory(UUID sessionId, UUID itemNumber, int quantity);

	public boolean placeOrder(UUID sessionId, Map<UUID, Integer> items, String mailingAddress);

	public void closeSession(UUID sessionId);
}
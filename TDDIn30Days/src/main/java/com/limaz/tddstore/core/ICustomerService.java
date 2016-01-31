package com.limaz.tddstore.core;

import java.util.UUID;

public interface ICustomerService
 {
	boolean addOrderToCustomer(UUID customerId, UUID orderId);
}

    
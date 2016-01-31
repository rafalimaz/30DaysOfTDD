package com.limaz.tddstore.core;

import java.util.UUID;

public interface ILoggingService {
	boolean logNewOrder(UUID orderId);
}

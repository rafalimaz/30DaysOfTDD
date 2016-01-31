package com.limaz.business.service;

import com.limaz.business.provider.IDataStoreProvider;
import com.limaz.business.provider.ILoggingProvider;

public class BusinessService {
	private IDataStoreProvider dataStoreProvider;
	private ILoggingProvider loggingProvider;
	
	public BusinessService(IDataStoreProvider dataStoreProvider, ILoggingProvider loggingProvider) {		
		this.dataStoreProvider = dataStoreProvider;
		this.loggingProvider = loggingProvider;
	}
}
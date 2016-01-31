package com.limaz.business;

public class BusinessService {
	private IDataStoreProvider dataStoreProvider;
	private ILoggingProvider loggingProvider;
	
	public BusinessService(IDataStoreProvider dataStoreProvider, ILoggingProvider loggingProvider) {		
		this.dataStoreProvider = dataStoreProvider;
		this.loggingProvider = loggingProvider;
	}
}
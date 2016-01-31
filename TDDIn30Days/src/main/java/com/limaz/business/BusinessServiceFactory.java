package com.limaz.business;

public class BusinessServiceFactory
 {
	public static BusinessService GetNewBusinessService() {
		IDataStoreProvider dataStoreProvider = GetNewDataStoreProvider();
		ILoggingProvider loggingProvider = GetNewLoggingProvider();		

		return new BusinessService(dataStoreProvider, loggingProvider);
	}

	private static IDataStoreProvider GetNewDataStoreProvider() {
		return new DataStoreProvider();
	}

	private static ILoggingProvider GetNewLoggingProvider() {
		return new LoggingProvider();
	}
}
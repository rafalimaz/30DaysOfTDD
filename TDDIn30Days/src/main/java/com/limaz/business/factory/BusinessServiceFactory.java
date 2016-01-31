package com.limaz.business.factory;

import com.limaz.business.provider.DataStoreProvider;
import com.limaz.business.provider.IDataStoreProvider;
import com.limaz.business.provider.ILoggingProvider;
import com.limaz.business.provider.LoggingProvider;
import com.limaz.business.service.BusinessService;

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
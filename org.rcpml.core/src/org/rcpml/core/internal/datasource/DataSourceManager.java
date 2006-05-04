package org.rcpml.core.internal.datasource;

import org.rcpml.core.datasource.IDataSource;

public class DataSourceManager {
	private static DataSourceManager sDataSourceManager;
	public static DataSourceManager getInstance() {
		if( sDataSourceManager == null ) {
			sDataSourceManager = new DataSourceManager();
		}
		return sDataSourceManager;
	}
	public IDataSource getDataSource(String src) {
		// TODO Auto-generated method stub
		return null;
	}
	public IDataSource getLocalDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.rcpml.core.datasource;

public interface IDataSource {
	void bind( IDataSourceElement object, String path );
	String getName();
}

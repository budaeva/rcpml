package org.rcpml.core.datasource;

import org.w3c.dom.Node;

public interface IDataSourceFactory {
	IDataSource createDataSource( Node node );
}

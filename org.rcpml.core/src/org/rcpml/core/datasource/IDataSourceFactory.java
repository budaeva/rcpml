package org.rcpml.core.datasource;

import java.net.URI;

import org.w3c.dom.Node;

public interface IDataSourceFactory {
	IDataSource createDataSource( URI uri, Node node );
}

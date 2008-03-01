package org.rcpml.core.datasource;

import org.rcpml.core.IController;
import org.w3c.dom.Node;

public interface IDataSourceFactory {
	
	IDataSource createDataSource( IController controller, Node node );
	
}

package org.rcpml.core.tests.datasource;

import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.datasource.IDataSource;
import org.w3c.dom.Node;

public class DataSourceTestBridge extends AbstractBridge {	
	protected DataSourceTestBridge(Node node, IController controller) {
		super(node, controller, true);
		
		String path = getAttribute("path");
		if( path != null && !path.equals("")) {
			IDataSource ds = getController().getDataSource(node, path);
			System.out.println("DataSourceTestBridge found:" + ds.getName() );
		}
	}

	public Object getPresentation() {		
		return "Cool";
	}
}

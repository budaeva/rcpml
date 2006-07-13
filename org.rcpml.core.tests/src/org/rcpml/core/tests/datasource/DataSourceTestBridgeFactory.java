package org.rcpml.core.tests.datasource;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class DataSourceTestBridgeFactory extends AbstractBridgeFactory {

	public IBridge createBridge(Node node) throws RCPMLException {
		return new DataSourceTestBridge(node, getController() );
	}
}

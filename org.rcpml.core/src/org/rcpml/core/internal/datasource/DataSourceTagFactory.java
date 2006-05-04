package org.rcpml.core.internal.datasource;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class DataSourceTagFactory extends AbstractBridgeFactory {

	public DataSourceTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new DataSourceBridge(node, this.getController());
	}
}

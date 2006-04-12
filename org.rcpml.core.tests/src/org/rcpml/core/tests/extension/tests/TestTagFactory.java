package org.rcpml.core.tests.extension.tests;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class TestTagFactory extends AbstractBridgeFactory {

	public TestTagFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new TestStyleBridge(node, this.getController());
	}
}

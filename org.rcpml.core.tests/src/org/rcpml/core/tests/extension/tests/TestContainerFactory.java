package org.rcpml.core.tests.extension.tests;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class TestContainerFactory extends AbstractBridgeFactory {

	public TestContainerFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {		
		return new TestContainerBridge(node, this.getController());
	}

}

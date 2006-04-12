package org.rcpml.core.tests.extension;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class FormTagBuilder extends AbstractBridgeFactory {

	public FormTagBuilder() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new TestBridge("form", node, this.getController());
	}
}

package org.rcpml.ui.internal.application;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class ApplicationTagFactory extends AbstractBridgeFactory {

	public ApplicationTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {	
		return new ApplicationBridge(node, this.getController() );
	}
}

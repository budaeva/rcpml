package org.rcpml.ui.internal.application;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class PerspectiveTagFactory extends AbstractBridgeFactory {

	public PerspectiveTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new PerspectiveBridge( node, this.getController() );
	}

}

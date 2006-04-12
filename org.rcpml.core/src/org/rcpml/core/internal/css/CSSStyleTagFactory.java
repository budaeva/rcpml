package org.rcpml.core.internal.css;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class CSSStyleTagFactory extends AbstractBridgeFactory {

	public CSSStyleTagFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new CSSStyleBridge( node, this.getController());
	}

}

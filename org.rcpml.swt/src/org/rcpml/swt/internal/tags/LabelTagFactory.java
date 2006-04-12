package org.rcpml.swt.internal.tags;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.swt.SWTLabelBridge;
import org.w3c.dom.Node;

public class LabelTagFactory extends AbstractBridgeFactory {	
	public LabelTagFactory() {
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new SWTLabelBridge( node, this.getController());
	}
}

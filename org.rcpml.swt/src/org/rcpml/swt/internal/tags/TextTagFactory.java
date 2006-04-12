package org.rcpml.swt.internal.tags;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.swt.SWTTextBridge;
import org.w3c.dom.Node;

public class TextTagFactory extends AbstractBridgeFactory {

	public IBridge createBridge(Node node) throws RCPMLException {
		return new SWTTextBridge(node, this.getController());
	}
}

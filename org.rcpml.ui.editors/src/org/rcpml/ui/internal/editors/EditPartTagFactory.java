package org.rcpml.ui.internal.editors;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class EditPartTagFactory extends AbstractBridgeFactory {
	public EditPartTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new EditorBridge(node, this.getController());
	}
}

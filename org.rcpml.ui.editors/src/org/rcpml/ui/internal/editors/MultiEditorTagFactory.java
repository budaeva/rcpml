package org.rcpml.ui.internal.editors;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 *
 */
public class MultiEditorTagFactory extends AbstractBridgeFactory {
	public MultiEditorTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new MultiEditorBridge(node, this.getController());
	}
}

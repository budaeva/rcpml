package org.rcpml.swt.internal.tags;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.swt.SWTSpinnerBridge;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 *
 */
public class SpinnerTagFactory extends AbstractBridgeFactory {

	public IBridge createBridge(Node node) throws RCPMLException {
		return new SWTSpinnerBridge(node, this.getController());
	}
}

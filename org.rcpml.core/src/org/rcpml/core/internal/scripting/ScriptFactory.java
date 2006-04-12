package org.rcpml.core.internal.scripting;

import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class ScriptFactory extends AbstractBridgeFactory {
	
	public ScriptFactory() {
	}

	public IBridge createBridge( Node node )
			throws RCPMLException {
		return new ScriptBridge( node, this.getController() );
	}
}

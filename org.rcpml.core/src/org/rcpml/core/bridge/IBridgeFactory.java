package org.rcpml.core.bridge;

import java.util.Map;

import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Node;

public interface IBridgeFactory {

	/**
	 * Creates appropriate bridge for specified node using parent.
	 * Can be called only then setContainer are done.
	 * Node can't be null. 
	 */
	IBridge createBridge( Node node ) throws RCPMLException;
	
	/**
	 * Set container. Container can be set only one time.
	 * If container are already set should throw exception.
	 */
	void setController( IController controller ) throws RCPMLException;
}

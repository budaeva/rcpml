package org.rcpml.core;

import org.w3c.dom.Node;

public interface IRenderer {

	/**
	 * Render specified node using object as a parent for rendered tree
	 * 
	 * @param node
	 * @param parent
	 * @return
	 */
	Object renderNode(Node node, Object parent);
}

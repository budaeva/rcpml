package org.rcpml.core;

import org.w3c.dom.Node;

public interface IRenderer {

	/**
	 * Render specified node using target as a parent for rendered tree
	 * 
	 * @param node
	 * @return
	 */
	Object renderNode(Node node, Object target);
}

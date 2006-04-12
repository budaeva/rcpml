package org.rcpml.core.bridge;

import org.w3c.dom.Node;

public interface IVisitor {
	/**
	 * Visit next element.
	 */
	void visit( Node node );
}

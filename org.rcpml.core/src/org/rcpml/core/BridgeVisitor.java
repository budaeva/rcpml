package org.rcpml.core;

import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.core.dom.DOMUtils;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 *
 */
public abstract class BridgeVisitor implements IVisitor {
	
	private IController parent;
	private String attr;
	
	public BridgeVisitor(IController parent, String attr) {
		this.parent = parent;
		this.attr = attr;
	}
	
	public void visit(Node node) {
		Node current = node;
		for (Node child = current.getFirstChild(); child != null; child = child
				.getNextSibling()) {
			parent.visit(child);
			IBridge bridge = parent.getBridge(child);
			if (bridge != null) {
				String value = attr == null ? null :
					DOMUtils.getAttribute(child, attr);
				visit(bridge, value);
			}
		}
		
	}
	
	protected abstract void visit(IBridge bridge, String value);

}

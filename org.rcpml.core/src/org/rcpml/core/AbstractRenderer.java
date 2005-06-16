package org.rcpml.core;

import org.w3c.dom.Node;

public abstract class AbstractRenderer implements IRenderer {
	
	private IController controller;
	
	protected AbstractRenderer(IController controller) {
		this.controller = controller;
	}
	
	protected final IController getController() {
		return controller;
	}

	protected final void renderNodeChildren(Node node, Object target) {
		for(Node n = node.getFirstChild();n != null;n = n.getNextSibling()) {
			controller.renderNode(n, target);			
		}
	}
	
}

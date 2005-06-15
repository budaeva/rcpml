package org.rcpml.core;

import org.w3c.dom.Node;

public abstract class AbstractRenderer implements IRenderer {
	
	private IRenderer parentRenderer;
	
	protected AbstractRenderer(IRenderer parent) {
		parentRenderer = parent;
	}

	protected final void renderNodeChildren(Node node, Object target) {
		for(Node n = node.getFirstChild();n != null;n = n.getNextSibling()) {
			parentRenderer.renderNode(n, target);			
		}
	}
	
}

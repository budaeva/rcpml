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
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			controller.renderNode(n, target);
		}
	}

	protected final String getAttribute(Node node, String attrName) {
		Node attr = node.getAttributes().getNamedItem(attrName);
		return (attr == null) ? null : attr.getNodeValue();
	}

	protected final String getChildrenAsText(Node node) {
		StringBuffer text = new StringBuffer();
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n.getNodeType() == Node.TEXT_NODE)
				text.append(n.getNodeValue());
		}
		return text.toString();
	}
}

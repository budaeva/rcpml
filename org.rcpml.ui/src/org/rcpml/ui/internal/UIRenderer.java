package org.rcpml.ui.internal;

import org.rcpml.core.IRenderer;
import org.w3c.dom.Node;

public class UIRenderer implements IRenderer {

	public Object renderNode(Node node, Object parent) {
		String name = node.getLocalName();
		if("view".equals(name))
			return new MLViewPart(node, (IRenderer) parent);
		return null;
	}
	
}

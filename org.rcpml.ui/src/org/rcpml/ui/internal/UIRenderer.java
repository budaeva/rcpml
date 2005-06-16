package org.rcpml.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import org.rcpml.core.IController;
import org.rcpml.core.IRenderer;
import org.rcpml.swt.SWTRenderer;
import org.w3c.dom.Node;

public class UIRenderer extends SWTRenderer implements IRenderer {	
	
	UIRenderer(IController controller) {
		super(controller);
	}

	public Object renderNode(Node node, Object target) {
		switch(node.getNodeType()) {
		
		case Node.TEXT_NODE:
			Label label = new Label((Composite)target, SWT.NONE);
			label.setText(node.getNodeValue());
			return label;
			
		case Node.ELEMENT_NODE:
			String name = node.getLocalName();
			if("view".equals(name))
				return new MLViewPart(node, this);
		}
		
		return super.renderNode(node, target);
	}
	
	// TODO revisit this later
	Control renderPartControl(Node node, Composite parent) {
		renderNodeChildren(node, parent);
		return null;
	}
}

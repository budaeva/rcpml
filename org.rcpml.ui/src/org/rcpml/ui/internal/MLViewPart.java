package org.rcpml.ui.internal;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;
import org.rcpml.core.IRenderer;
import org.w3c.dom.Node;

public class MLViewPart extends ViewPart {

	private Node node;
	private IRenderer renderer;
	private Control control;
	
	public MLViewPart(Node node, IRenderer renderer) {
		this.node = node;
		this.renderer = renderer;
	}

	public void createPartControl(Composite parent) {
		Node child = node.getFirstChild();
		if(child != null)
			control = (Control) renderer.renderNode(child, parent);
	}

	public void setFocus() {
		if(control != null)
			control.setFocus();
	}

}

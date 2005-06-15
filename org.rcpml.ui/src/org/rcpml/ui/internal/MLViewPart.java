package org.rcpml.ui.internal;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;
import org.w3c.dom.Node;

public class MLViewPart extends ViewPart {

	private Node node;
	private UIRenderer renderer;
	private Control control;
	
	public MLViewPart(Node node, UIRenderer renderer) {
		this.node = node;
		this.renderer = renderer;
	}

	public void createPartControl(Composite parent) {
		control = renderer.renderPartControl(node, parent);
	}

	public void setFocus() {
		if(control != null)
			control.setFocus();
	}

}

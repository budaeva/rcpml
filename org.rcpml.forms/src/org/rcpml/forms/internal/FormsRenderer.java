package org.rcpml.forms.internal;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IRenderer;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Node;

public class FormsRenderer implements IRenderer {
	
	private FormToolkit toolkit;
	
	private FormToolkit getToolkit() {
		if(toolkit == null) {
			Display display = 
				FormsPlugin.getDefault().getWorkbench().getDisplay();
			toolkit = new FormToolkit(new FormColors(display));
		}
		return toolkit;
	}

	public Object renderNode(Node node, Object target) {
		String name = node.getLocalName();
		if("form".equals(name)) {
			return getToolkit().createForm((Composite) target);
		}
		throw new RCPMLException("unknown node: " + name);
	}

}

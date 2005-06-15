package org.rcpml.forms.internal;

import java.awt.GridLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.rcpml.core.IRenderer;
import org.rcpml.swt.SWTRenderer;

import org.w3c.dom.Node;

public class FormsRenderer extends SWTRenderer implements IRenderer {

	private FormToolkit toolkit;

	FormsRenderer(IRenderer parent) {
		super(parent);
	}

	private FormToolkit getToolkit() {
		if (toolkit == null) {
			Display display = PlatformUI.getWorkbench().getDisplay();
			toolkit = new FormToolkit(new FormColors(display));
		}
		return toolkit;
	}

	public Object renderNode(Node node, Object target) {
		Composite parent = (Composite) target;
		String name = node.getLocalName();
		if ("form".equals(name)) {
			Form form = getToolkit().createForm(parent);
			setAttributes(node, form);
			TableWrapLayout layout = new TableWrapLayout();
			form.getBody().setLayout(layout);
			renderNodeChildren(node, form.getBody());
			return form;
		}
		if ("hyperlink".equals(name)) {
			return getToolkit().createHyperlink(parent,
					getAttribute(node, ATTR_TEXT), SWT.WRAP);
		}
		return null;
	}

}

package org.rcpml.forms.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Node;

public class MLFormEditor extends FormEditor {
	
	private Node node;
	private FormsRenderer renderer;

	MLFormEditor(Node node, FormsRenderer renderer) {
		this.node = node;
		this.renderer = renderer;
	}
	
	protected FormToolkit createToolkit(Display display) {
		return renderer.getToolkit(display);
	}

	protected void addPages() {
		for(Node n = node.getFirstChild();n != null;n = n.getNextSibling()) {
			Object child = renderer.renderNode(n, this);
			if(child instanceof Control) {
				addPage((Control) child);
			} else if(child instanceof IFormPage) {
				try {
					addPage((IFormPage) child);
				} catch(PartInitException pie) {
					pie.printStackTrace();
					throw new RCPMLException(pie.getMessage());
				}
			}
		}
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public void doSaveAs() {
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

}

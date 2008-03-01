package org.rcpml.ui.internal.editors;

import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IVisitor;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 *
 */
public class MultiEditorBridge extends AbstractBridge {
	private IBridgeEditorPart fEditPart;

	protected MultiEditorBridge(Node node, IController container) {
		super(node, container);
		fEditPart = new BridgeMultiEditPart(node, container, this);
	}

	public void build() {
		this.visitAllChildrens(this.getController());
	}

	public Object getPresentation() {
		return this.fEditPart;
	}

	public void visit(IVisitor visitor) {
		if (this.fEditPart.isInitialized()) {
			this.visitAllChildrens(visitor);
		}
	}

	public void update() {
		Composite composite = this.fEditPart.getComposite();
		if (composite != null) {
			composite.layout();
		}
	}
	
	public void disposeBridge() {
		this.getController().bridgeDisposed(this);
	}
}
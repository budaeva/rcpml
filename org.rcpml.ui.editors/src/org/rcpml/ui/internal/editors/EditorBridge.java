/**
 * 
 */
package org.rcpml.ui.internal.editors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.EditorPart;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.core.css.RCPCSSConstants;
import org.w3c.dom.Node;

class EditorBridge extends AbstractBridge {
	private static final String DEBUG = "rcpmldebugmode";
	private IBridgeEditorPart fEditPart;

	protected EditorBridge(Node node, IController container) {
		super(node, container);
		String debugMode = getAttribute(DEBUG);
		if( !debugMode.equals(RCPCSSConstants.TRUE_VALUE)) {
			fEditPart = new BridgeEditPart(this);
		}
		else {
			fEditPart = new MultiBridgeEditPart(this);
		}
	}

	public void executeInitScript( EditorPart part ) {
		this.getController().getScriptManager().getDefaultContext().bindObject("editor", part );
		executeScript("oninit");
	}

	private void executeScript( String attrName ) {
		String initScript = getAttribute( attrName );
		if( initScript.length() > 0 ) {
			this.getController().getScriptManager().executeScript( initScript );
		}
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
package org.rcpml.core.tests.extension;

import org.apache.batik.dom.events.DOMUIEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.DOMUtils;
import org.w3c.dom.Node;

public class LabelTagBuilder extends AbstractBridgeFactory {	
	class LabelBridge extends AbstractBridge {
		private Label fLabel;
		protected LabelBridge(Node node, IController container) {
			super( node, container, false );
			IBridge parent = this.getParent();
			Object parentPresentation = parent.getPresentation();
			if( parent != null && parentPresentation instanceof Composite ) {
				Composite composite = (Composite)parentPresentation;				
				this.fLabel = new Label(composite, SWT.NONE );
				this.fLabel.setText( DOMUtils.getChildrenAsText(node));
			}
			else {
				throw new RCPMLException("Cant build bridge. Parent interface mismatch.");
			}
		}
		public Object getPresentation() {
			return this.fLabel;
		}
	}
	public LabelTagBuilder() {
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new LabelBridge( node, this.getController());
	}

}

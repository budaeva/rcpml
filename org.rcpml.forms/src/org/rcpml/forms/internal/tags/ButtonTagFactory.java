package org.rcpml.forms.internal.tags;

import org.eclipse.swt.SWT;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class ButtonTagFactory extends AbstractBridgeFactory {		

	class ButtonBridge extends AbstractEclipseFormsButtonBridge {		
		
		protected ButtonBridge(Node node, IController controller ) {
			super( node, controller );
		}
		protected int getStyle() {
			return SWT.PUSH;
		}					
	}
	
	public ButtonTagFactory() {
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ButtonBridge( node, this.getController() );
	}	
}

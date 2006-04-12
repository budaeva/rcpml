package org.rcpml.core.tests.extension;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ShellTagBuilder extends AbstractBridgeFactory {

	private static class ShellBridge extends AbstractBridge {		
		private static final String TITLE_ATTR = "title";
		private Shell fShell;
		public ShellBridge( Node node, IController container ) {
			super( node, container );
			this.fShell = new Shell( );		
			this.fShell.setLayout(new GridLayout());
			
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element element  = (Element)node;
				String title = element.getAttribute(TITLE_ATTR);
				if( title != null ) {
					this.fShell.setText(title);
				}
			}
		}		
		public Composite getComposite() {
			return this.fShell;
		}		
		public Object getPresentation() {
			return this.fShell;
		}
		@Override
		public void update() {
			this.fShell.layout();
		}
	}
	public ShellTagBuilder() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ShellBridge( node, this.getController() );
	}	
	
}

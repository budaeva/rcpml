package org.rcpml.core.tests.extension;

import org.eclipse.swt.SWT;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class CheckTagBridge extends AbstractBridgeFactory {

	private static class CheckBridge extends AbstractSWTButtonBridge {

		private static final String TITLE_ATTR = "title";
		private static final String ENABLED_ATTR = "enabled";

		protected CheckBridge(Node node, IController controller ) {
			super( node, controller );
			System.out.println("Derived Checkbox created");			
		}

		@Override
		protected int getStyle() {
			return SWT.CHECK;
		}

		@Override
		protected String getTitle() {
			return this.getAttribute(TITLE_ATTR);
		}

		@Override
		protected boolean getEnabled() {
			String enabled = this.getAttribute(ENABLED_ATTR);
			if( enabled.equals("false") ) {
				return false;
			}
			return true;
		}

		@Override
		protected void initHandlers() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public CheckTagBridge() {
		super();	
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new CheckBridge( node, this.getController() );
	}

}

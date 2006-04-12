package org.rcpml.core.tests.extension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class ButtonTagBuilder extends AbstractBridgeFactory {		

	public static final String TITLE_ATTR = "title";

	class ButtonBridge extends AbstractSWTButtonBridge {		
		private String fOnClickAction;
		protected ButtonBridge(Node node, IController controller ) {
			super( node, controller );
		}
		@Override
		protected int getStyle() {
			return SWT.PUSH;
		}

		@Override
		protected String getTitle() {
			return this.getAttribute(TITLE_ATTR);
		}
		@Override
		protected boolean getEnabled() {			
			return true;
		}
		@Override
		protected void initHandlers() {
			Button button = this.getButton();			
			this.fOnClickAction = this.getAttribute("onclick");
			if( this.fOnClickAction.length() > 0 ) {
				button.addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						getController().getScriptManager().executeScript( fOnClickAction );
					}					
				});
			}
		}
	}
	
	public ButtonTagBuilder() {
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ButtonBridge( node, this.getController() );
	}	
}

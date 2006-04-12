package org.rcpml.core.tests.extension;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public abstract class AbstractSWTButtonBridge extends AbstractBridge {
	private Button fButton;

	protected AbstractSWTButtonBridge(Node node, IController controller) {
		super(node, controller, false);
		IBridge parent = this.getParent();
		Object presentation = parent.getPresentation();
		if ( presentation instanceof Composite) {
			// check initial values.
			int style = this.getStyle();
			this.fButton = new Button((Composite) presentation, style);
			
			update();
			
			initHandlers();
		}
	}
	protected abstract int getStyle();
	protected abstract String getTitle();
	protected abstract boolean getEnabled();
	protected abstract void initHandlers();

	public Object getPresentation() {
		return this.fButton;
	}
	
	protected Button getButton() {
		return this.fButton;
	}
	@Override
	public void update() {
		String title = this.getTitle();
		if( title != null ) {
			this.fButton.setText(title);
		}
		
		this.fButton.setEnabled(this.getEnabled());
		
		this.fButton.update();
	}
	@Override
	public void dispose() {
		if( this.fButton != null ) {
			this.fButton.dispose();
			this.fButton = null;
		}
	}
}

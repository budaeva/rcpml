package org.rcpml.forms.internal.tags;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.rcpml.forms.internal.ITookitHolder;
import org.rcpml.swt.AbstractSWTButtonBridge;
import org.w3c.dom.Node;

public abstract class AbstractEclipseFormsButtonBridge extends AbstractSWTButtonBridge implements ITookitHolder {
	
	private FormToolkit fFormToolkit;
	
	protected AbstractEclipseFormsButtonBridge(Node node, IController controller) {
		super(node, controller);			
	}
		
	protected String getTitle() {
		return this.getAttribute(TITLE_ATTR);
	}
	
	protected Button createButton( Composite parent, String title, int style ) {
		return this.getFormToolkit().createButton(parent, title, style );
	}	

	public FormToolkit getFormToolkit() {
		if( this.fFormToolkit == null ) {
			IBridge parentBridge = this.getParent();
			
			fFormToolkit = EclipseFormsUtil.constructFormToolkit(parentBridge);
		}
		return this.fFormToolkit;
	}
	protected Object constructLayoutData(Composite parent) {
		return AbstractEclipseFormsBridge.constructFormsLayuotData(parent, getNode()); 
	}		
}

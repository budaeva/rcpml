package org.rcpml.forms.internal;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.swt.AbstractSWTBridge;
import org.w3c.dom.Node;

public abstract class AbstractEclipseFormsBridge extends AbstractSWTBridge
		implements ITookitHolder {

	protected FormToolkit fFormToolkit;

	protected AbstractEclipseFormsBridge(Node node, IController controller,
			boolean visitChilds) {
		super(node, controller, visitChilds);
	}

	public FormToolkit getFormToolkit() {
		if (this.fFormToolkit == null) {
			IBridge parentBridge = this.getParent();

			fFormToolkit = EclipseFormsUtil.constructFormToolkit(parentBridge);
		}
		return this.fFormToolkit;
	}
	
	protected Object constructLayoutData( Composite parent ) {
		return constructFormsLayuotData(parent, this.getNode());
	}
	
	public static Object constructFormsLayuotData(Composite parent, Node node) {

		RCPStylableElement stylable = (RCPStylableElement)node;
		Object layout =  parent.getLayout();
		
		Object layoutData = EclipseFormsUtil.constructFormsLayoutData(stylable, layout);
		if( layoutData == null ) {
			return AbstractSWTBridge.constructSWTLayoutData(parent, node);
		}
		return layoutData;
	}	
}

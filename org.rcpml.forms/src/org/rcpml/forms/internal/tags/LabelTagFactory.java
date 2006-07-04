package org.rcpml.forms.internal.tags;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.rcpml.forms.internal.ITookitHolder;
import org.rcpml.swt.SWTLabelBridge;
import org.w3c.dom.Node;

public class LabelTagFactory extends AbstractBridgeFactory {
	private static class LabelBridge extends SWTLabelBridge implements ITookitHolder {
		private FormToolkit fFormToolkit;
		
		public LabelBridge(Node node, IController container) {
			super(node, container);
		}

		protected Label constructLabel(Composite parent, int style) {
			return this.getFormToolkit().createLabel( parent, DOMUtils.getChildrenAsText( this.getNode() ), style );
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
	public LabelTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new LabelBridge(node, this.getController());
	}

}

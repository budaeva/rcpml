package org.rcpml.forms.internal.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.rcpml.forms.internal.ITookitHolder;
import org.rcpml.swt.SWTTextBridge;
import org.w3c.dom.Node;

public class TextTagFactory extends AbstractBridgeFactory {

	private static class TextBridge extends SWTTextBridge implements ITookitHolder {
		private FormToolkit fFormToolkit;

		protected TextBridge(Node node, IController controller) {
			super(node, controller);
		}

		protected Text createText( Composite parent ) {
			return this.getFormToolkit().createText(parent, "", getStyle() );			
		}

		public FormToolkit getFormToolkit() {
			if( this.fFormToolkit == null ) {
				IBridge parentBridge = this.getParent();
				
				fFormToolkit = EclipseFormsUtil.constructFormToolkit(parentBridge);
			}
			return this.fFormToolkit;
		}
		protected Object constructLayoutData(Composite parent ) {
			return AbstractEclipseFormsBridge.constructFormsLayuotData(parent, this.getNode() );
		}
	}

	public TextTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new TextBridge(node, this.getController());
	}

}

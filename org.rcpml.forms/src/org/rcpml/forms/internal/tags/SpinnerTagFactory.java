package org.rcpml.forms.internal.tags;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.rcpml.forms.internal.ITookitHolder;
import org.rcpml.swt.SWTSpinnerBridge;
import org.w3c.dom.Node;

public class SpinnerTagFactory extends AbstractBridgeFactory {

	private static class SpinnerBridge extends SWTSpinnerBridge implements ITookitHolder {
		private FormToolkit fFormToolkit;

		protected SpinnerBridge(Node node, IController controller) {
			super(node, controller);
		}
		
		protected Spinner constructSpinner(Composite parent, int style) {
			Spinner spinner = super.constructSpinner(parent, style);
			this.getFormToolkit().adapt(spinner, false, false);
			return spinner;
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

	public SpinnerTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new SpinnerBridge(node, this.getController());
	}

}

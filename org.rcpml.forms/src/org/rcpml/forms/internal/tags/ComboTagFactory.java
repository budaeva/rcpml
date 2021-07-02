package org.rcpml.forms.internal.tags;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.rcpml.forms.internal.ITookitHolder;
import org.rcpml.swt.SWTComboBridge;
import org.w3c.dom.Node;

public class ComboTagFactory extends AbstractBridgeFactory {
	private static class ComboBridge extends SWTComboBridge implements ITookitHolder {
		private FormToolkit fFormToolkit;
		
		public ComboBridge(Node node, IController container) {
			super(node, container);
		}

		protected Combo constructCombo(Composite parent, int style) {
			Combo combo = new Combo(parent, style);
			String label = getAttribute("label");
			combo.setToolTipText(label);
			
			combo.setBackground(this.getFormToolkit().getColors().getBackground());
//			combo.setForeground(this.getFormToolkit().getColors().getBorderColor());
			return combo;
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
	public ComboTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ComboBridge(node, this.getController());
	}

}

package org.rcpml.forms.internal.tags;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.rcpml.swt.ICompositeParentConstructor;
import org.w3c.dom.Node;

public class CompositeTagFactory extends AbstractBridgeFactory {
	private static class CompositeBridge extends AbstractEclipseFormsBridge
			implements ICompositeParentConstructor {
		private Composite fComposite;

		protected CompositeBridge(Node node, IController controller) {
			super(node, controller, true);
		}

		protected void construct(Composite parent) {
			fComposite = getFormToolkit().createComposite(parent);
			this.fComposite.setLayout(EclipseFormsUtil
					.constructLayout((RCPStylableElement) this.getNode()));
			this.fComposite.setLayoutData(this.constructLayout(parent));
		}

		public Object getPresentation() {
			return this.fComposite;
		}

		public Object createInstance(Composite parent) {
			fFormToolkit = new FormToolkit(new FormColors(parent.getDisplay()));

			construct(parent);
			this.getController().visit(getNode());
			return getPresentation();
		}

		public Object createInstance(Object[] args) {
			if (args.length == 1 && args[0] instanceof Composite) {
				return createInstance((Composite) args[0]);
			}
			return null;
		}
		public void update() {	
			if( this.fComposite != null ) {
				this.fComposite.layout();				
			}
		}
	}

	public CompositeTagFactory() {
		// TODO Auto-generated constructor stub
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new CompositeBridge(node, this.getController());
	}
}

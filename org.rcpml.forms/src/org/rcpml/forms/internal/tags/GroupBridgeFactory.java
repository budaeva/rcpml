package org.rcpml.forms.internal.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.w3c.dom.Node;

public class GroupBridgeFactory extends AbstractBridgeFactory {
	private static class GroupBridge extends AbstractEclipseFormsBridge {
		private static final String TITLE_ATTR = RCPMLTagConstants.TITLE_ATTR;
		private Group fGroup;

		protected GroupBridge(Node node, IController controller) {
			super(node, controller, true);
		}

		protected void construct(Composite parent) {
			this.fGroup = new Group(parent, SWT.NONE);
			getFormToolkit().adapt(this.fGroup);
			String title = getAttribute(TITLE_ATTR);
			if( title != null ) {
				this.fGroup.setText(title);
			}
			update();
		}

		public Object getPresentation() {
			return this.fGroup;
		}

		public void update() {
			if (this.fGroup != null) {
				this.fGroup.setLayout(EclipseFormsUtil
						.constructLayout((RCPStylableElement) this.getNode()));
				this.fGroup.setLayoutData(this.constructLayoutData(this.fGroup
						.getParent()));
			}
		}

	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new GroupBridge(node, this.getController());
	}
}

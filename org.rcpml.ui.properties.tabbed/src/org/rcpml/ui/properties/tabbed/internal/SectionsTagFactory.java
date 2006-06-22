package org.rcpml.ui.properties.tabbed.internal;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.swt.ICompositeHolder;
import org.w3c.dom.Node;

public class SectionsTagFactory extends AbstractBridgeFactory {
	private static class BridgeSection extends AbstractPropertySection implements
			ICompositeHolder {
		private Composite fComposite;

		SectionBridge bridge;

		private boolean fInitialized = false;

		public BridgeSection(SectionBridge bridge) {
			super();
			this.bridge = bridge;
		}

		public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
			super.createControls(parent, aTabbedPropertySheetPage);
			createPartControl(parent);
		}

		public void createPartControl(Composite parent) {
			fComposite = getWidgetFactory().createComposite(parent);
			this.fComposite.setLayout(new FillLayout());
			this.bridge.build();
			fInitialized = true;
		}

		public boolean isInitialized() {
			return fInitialized;
		}

		public void setFocus() {
			this.fComposite.setFocus();
		}

		public Composite getComposite() {
			return this.fComposite;
		}

		public void dispose() {
			bridge.disposeBridge();
		}
	}

	private static class SectionBridge extends AbstractBridge {
		private BridgeSection fSection;

		protected SectionBridge(Node node, IController container) {
			super(node, container);
			fSection = new BridgeSection(this);
		}

		public void build() {
			this.visitAllChildrens(this.getController());
		}

		public Object getPresentation() {
			return this.fSection;
		}

		public void visit(IVisitor visitor) {
			if (this.fSection.isInitialized()) {
				this.visitAllChildrens(visitor);
			}
		}

		public void update() {
			//TODO: Add some code here
//			Composite composite = this.fSection.getComposite();
//			if (composite != null) {
//				composite.layout();
//			}
		}

		public void disposeBridge() {
			this.getController().bridgeDisposed(this);
		}
		
		public FormToolkit getFormToolkit() {
			return fSection.getWidgetFactory();
		}
	}

	public SectionsTagFactory() {
		// TODO Auto-generated constructor stub
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new SectionBridge(node, getController());
	}
}

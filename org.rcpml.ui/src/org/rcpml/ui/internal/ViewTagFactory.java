package org.rcpml.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.swt.ICompositeHolder;
import org.w3c.dom.Node;

public class ViewTagFactory extends AbstractBridgeFactory {
	private static class BridgeViewPart extends ViewPart implements
			ICompositeHolder {
		private Composite fComposite;

		ViewBridge bridge;

		private boolean fInitialized = false;

		public BridgeViewPart(ViewBridge bridge) {
			super();
			this.bridge = bridge;
		}

		public void createPartControl(Composite parent) {
			fComposite = new Composite(parent, SWT.NONE);
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

	private static class ViewBridge extends AbstractBridge {
		private BridgeViewPart fViewPart;

		protected ViewBridge(Node node, IController container) {
			super(node, container);
			fViewPart = new BridgeViewPart(this);
		}

		public void build() {
			this.visitAllChildrens(this.getController());
		}

		public Object getPresentation() {
			return this.fViewPart;
		}

		public void visit(IVisitor visitor) {
			if (this.fViewPart.isInitialized()) {
				this.visitAllChildrens(visitor);
			}
		}

		public void update() {
			Composite composite = this.fViewPart.getComposite();
			if (composite != null) {
				composite.layout();
				composite.update();
			}
		}
		
		public void disposeBridge() {
			this.getController().bridgeDisposed(this);
		}
	}

	public ViewTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ViewBridge(node, this.getController());
	}

}

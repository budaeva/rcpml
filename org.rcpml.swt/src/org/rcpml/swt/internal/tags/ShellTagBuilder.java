package org.rcpml.swt.internal.tags;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.swt.ICompositeHolder;
import org.rcpml.swt.SWTUtils;
import org.w3c.dom.Node;

public class ShellTagBuilder extends AbstractBridgeFactory {

	private static class ShellBridge extends AbstractBridge {
		private static final String TITLE_ATTR = "title";

		private Shell fShell;

		public ShellBridge(Node node, IController container) {
			super(node, container, true);
			IBridge parentBridge = this.getParent();

			Composite composite = null;

			if (parentBridge != null) {
				Object presentation = parentBridge.getPresentation();
				if (presentation != null) {
					if (presentation instanceof Composite) {
						composite = (Composite) presentation;
					} else if (presentation instanceof ICompositeHolder) {
						composite = ((ICompositeHolder) presentation)
								.getComposite();
					}
				}
			}
			if( composite != null && !( composite instanceof Shell ) ) {
				throw new RCPMLException("Shell parent can't be not shell.");
			}
			this.construct(composite);
		}

		@Override
		public void update() {
			this.fShell.layout();
			String title = this.getAttribute(TITLE_ATTR);
			if (title != null) {
				this.fShell.setText(title);
			}
		}

		protected void construct(Composite parent) {			
			this.fShell = new Shell();
			this.fShell.setLayout(SWTUtils.constructLayout((RCPStylableElement) this.getNode()));
			this.fShell.addDisposeListener( new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					getController().bridgeDisposed(ShellBridge.this);
				}
				
			});
			update();
		}

		public Object getPresentation() {
			return this.fShell;
		}
	}

	public ShellTagBuilder() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ShellBridge(node, this.getController());
	}

}

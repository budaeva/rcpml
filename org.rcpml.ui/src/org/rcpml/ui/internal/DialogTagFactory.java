package org.rcpml.ui.internal;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.ICompositeHolder;
import org.rcpml.core.bridge.IVisitor;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 * 
 */
public class DialogTagFactory extends AbstractBridgeFactory {
	
	private static final String TITLE_ATTR = "title";
	private static final String WIDTH_ATTR = "width";
	private static final String HEIGHT_ATTR = "height";

	private static class RCPMLDialog extends Dialog implements ICompositeHolder {
		private Composite fComposite;

		DialogBridge bridge;
		private int width;
		private int height;
		private String title;

		private boolean fInitialized = false;

		public RCPMLDialog(DialogBridge bridge, int width, int height, String title) {
			super(new Shell());
	        setShellStyle(getShellStyle() | SWT.RESIZE);
			this.bridge = bridge;
			this.width = width;
			this.height = height;
			this.title = title;
		}
		
		protected Control createDialogArea(Composite parent) {
			getShell().setText(title);
			fComposite = (Composite)super.createDialogArea(parent);
			fInitialized = true;
			this.fComposite.setLayout(new GridLayout(2, false));
			this.bridge.build();
		    return fComposite;
		}
		
		protected Control createContents(Composite parent) {
			Control content = super.createContents(parent);
			getShell().setSize(width, height);
			update();
			setFocus();
			return content;
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
		
		public void update() {
			if (getShell() != null) {
				getShell().update();
				getShell().layout();
			}
		}

		public void dispose() {
			bridge.disposeBridge();
		}
	}

	private static class DialogBridge extends AbstractBridge {
		private RCPMLDialog fDialog;

		protected DialogBridge(Node node, IController container) {
			super(node, container);
			fDialog = new RCPMLDialog(this,
			                            getIntAttribute(WIDTH_ATTR),
			                            getIntAttribute(HEIGHT_ATTR),
			                            this.getAttribute(TITLE_ATTR));
		}
		
		private int getIntAttribute(String name) {
			try {
				return Integer.parseInt(getAttribute(name));
            }
            catch (Exception e) {
            }
        	return -1;
		}

		public void build() {
			this.visitAllChildrens(this.getController());
		}

		public Object getPresentation() {
			return this.fDialog;
		}

		public void visit(IVisitor visitor) {
			if (this.fDialog.isInitialized()) {
				this.visitAllChildrens(visitor);
			}
		}

		public void update() {
			this.fDialog.update();
		}

		public void disposeBridge() {
			this.getController().bridgeDisposed(this);
		}
	}

	public DialogTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new DialogBridge(node, this.getController());
	}

}

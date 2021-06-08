package org.rcpml.swt.internal.tags;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.swt.ICompositeParentConstructor;
import org.w3c.dom.Node;

import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

public class ShellTagBuilder extends AbstractBridgeFactory {
	private static class ShellControl {
		private Shell fShell;

		public ShellControl(Shell shell) {
			this.fShell = shell;
		}

		public void close() {
			this.fShell.close();
		}

		public void pack() {
			this.fShell.pack();
		}
	}

	private static class ShellBridge extends AbstractBridge implements
			ICompositeParentConstructor {
		private static final String TITLE_ATTR = RCPMLTagConstants.TITLE_ATTR;
		public static final String RESIZE_ATTR = "resize";

		private Shell fShell;

		public ShellBridge(Node node, IController container) {
			super(node, container, false);
		}

		public void update() {
			if (this.fShell != null) {
				// this.fShell.setLayout(SWTUtils
				// .constructLayout((RCPStylableElement) this.getNode()));
				this.fShell.setLayout(new FillLayout());

				RCPStylableElement stylable = (RCPStylableElement) this
						.getNode();
				Value widthValue = stylable
						.getComputedValue(RCPCSSConstants.LAYOUT_WIDTH_INDEX);
				Value heightValue = stylable
						.getComputedValue(RCPCSSConstants.LAYOUT_HEIGHT_INDEX);

				int width = (int) widthValue.getFloatValue();
				int height = (int) heightValue.getFloatValue();
				boolean noResize = false;
				if (width != -1 && height != -1) {
					this.fShell.setSize(width, height);
				}

				this.fShell.layout();
				this.fShell.update();
				String title = this.getAttribute(TITLE_ATTR);
				if (title != null) {
					this.fShell.setText(title);
				}
			}
		}

		protected void construct(Composite parent) {
			this.fShell = new Shell();
			initialize();
		}

		private void initialize() {
			this.fShell.addDisposeListener(new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					getController().bridgeDisposed(ShellBridge.this);
				}

			});
			IScriptingContext context;
//			try {
				context = getController().getScriptManager()
						.getDefaultContext();
//			} catch (ScriptException e1) {
//				e1.printStackTrace();
//				context = null;
//			}
			if (context != null) {
				context.bindObject("Shell", new ShellControl(fShell));
				this.visitAllChildrens(getController());
				getController().update();
			}
		}

		protected void construct(Display display) {
			int style = SWT.DIALOG_TRIM;
			String resize = this.getAttribute(RESIZE_ATTR);
			if (resize != null) {
				if (resize.equals(RCPCSSConstants.TRUE_VALUE)) {
					style |= SWT.RESIZE | SWT.MAX | SWT.MIN;
				}
			}
			this.fShell = new Shell(display, style);
			initialize();
		}

		public void visit(IVisitor visitor) {
			if (this.fShell != null) {
				this.visitAllChildrens(visitor);
			}
		}

		public Object getPresentation() {
			return this.fShell;
		}

		public Object createInstance(Composite parent) {
			construct(parent);
			this.getController().update();
			return getPresentation();
		}

		public Object createInstance(Object[] args) {
			if (args.length == 1 && args[0] instanceof Composite) {
				construct((Composite) args[0]);
				this.getController().update();
				return getPresentation();
			}
			if (args.length == 1 && args[0] instanceof Display) {
				construct((Display) args[0]);
				this.getController().update();
				return getPresentation();
			}
			return null;
		}
	}

	public ShellTagBuilder() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ShellBridge(node, this.getController());
	}

}

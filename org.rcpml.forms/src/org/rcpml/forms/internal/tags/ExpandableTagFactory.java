package org.rcpml.forms.internal.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.w3c.dom.Node;

public class ExpandableTagFactory extends AbstractBridgeFactory {
	private static class ExpandableBridge extends AbstractEclipseFormsBridge {
		private static final String TYPE_ATTR = "type";

		private static final String TREE_STYLE = "tree";

		private static final String TWISTIE_STYLE = "twistie";

		private static final String TITLE_ATTR = "title";

		private ExpandableComposite fExpandableComposite;

		private Composite fComposite;

		protected ExpandableBridge(Node node, IController controller) {
			super(node, controller, true);
		}

		protected void construct(Composite parent) {
			int style = ExpandableComposite.EXPANDED;
			String styleAttr = this.getAttribute(TYPE_ATTR);
			if (styleAttr.equals(TREE_STYLE)) {
				style |= ExpandableComposite.TREE_NODE;
			} else if (styleAttr.equals(TWISTIE_STYLE)) {
				style |= ExpandableComposite.TREE_NODE;
			}
			String title = this.getAttribute(TITLE_ATTR);

			this.fExpandableComposite = this.getFormToolkit()
					.createExpandableComposite(parent, style);
			if (title != null) {
				this.fExpandableComposite.setText(title);
			}			

			this.fExpandableComposite.setLayoutData(this
					.constructLayoutData(parent));

			this.fExpandableComposite.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					disposeDataBinding();
				}
			});
			this.fExpandableComposite.setLayout(new FillLayout());
			this.fComposite = this.getFormToolkit().createComposite(
					this.fExpandableComposite, SWT.NONE);
			this.fComposite.setLayout(EclipseFormsUtil
					.constructLayout((RCPStylableElement) this.getNode()));
			this.fExpandableComposite.setClient(fComposite);

			this.fExpandableComposite
					.addExpansionListener(new ExpansionAdapter() {
						public void expansionStateChanged(ExpansionEvent e) {
							getController().update();
						}
					});
		}

		public Object getPresentation() {
			return this.fComposite;
		}

		public void update() {
			if (this.fExpandableComposite != null) {
				this.fExpandableComposite.setLayoutData(this
						.constructLayoutData(this.fExpandableComposite
								.getParent()));
				this.fComposite.layout();
				this.fExpandableComposite.layout();
			}
		}
	}

	public ExpandableTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new ExpandableBridge(node, this.getController());
	}
}

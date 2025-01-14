package org.rcpml.forms.internal.tags;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.rcpml.forms.internal.EclipseFormsUtil;
import org.w3c.dom.Node;

public class SectionTagFactory extends AbstractBridgeFactory {
	private class SectionBridge extends AbstractEclipseFormsBridge {
		private static final String TITLE_ATTR = RCPMLTagConstants.TITLE_ATTR;

		private static final String DESCRIPTION_ATTR = RCPMLTagConstants.DESCRIPTION_ATTR;

		private Section fSection;

		private Composite fComposite;

		protected SectionBridge(Node node, IController controller) {
			super(node, controller, true);
		}

		protected void construct(Composite parent) {
			String title = this.getAttribute(TITLE_ATTR);
			String description = this.getAttribute(DESCRIPTION_ATTR);
			int style = Section.TWISTIE | Section.FOCUS_TITLE
					| Section.EXPANDED;

			if (title != null && title.length() > 0) {
				style |= Section.TITLE_BAR;
			}
			if (description != null && description.length() > 0) {
				style |= Section.DESCRIPTION;
			}
			this.fSection = this.getFormToolkit().createSection(parent, style);
			this.fSection.setText(title);
			this.fSection.setDescription(description);
			this.fSection.setTitleBarForeground(this.getFormToolkit().getColors().getForeground());
			this.fSection.setLayout( new GridLayout() );
			this.fComposite = this.getFormToolkit().createComposite(
					this.fSection);
			this.fComposite.setLayout(EclipseFormsUtil
					.constructLayout((RCPStylableElement) this.getNode()));
			//this.fComposite.setLayoutData(this.constructLayout(parent));
			this.fSection.setClient(this.fComposite);
			//this.fSection.setLayoutData(this.constructLayoutData(parent));

			fSection.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					disposeDataBinding();
				}
			});			
		}

		public void update() {
			if (this.fSection != null) {
				Control client = this.fSection.getClient();
				if (client != null && client instanceof Composite) {
					((Composite) client).layout();
				}
				this.fSection.layout();				
			}
		}

		public Object getPresentation() {
			return this.fComposite;
		}

	}

	public SectionTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new SectionBridge(node, this.getController());
	}

}

package org.rcpml.forms.internal.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.forms.internal.AbstractEclipseFormsBridge;
import org.w3c.dom.Node;

import com.xored.scripting.core.ScriptException;

public class HyperLinkTagFactory extends AbstractBridgeFactory {
	private static class HyperLinkBridge extends AbstractEclipseFormsBridge {
		protected static final String ONCLICK_SCRIPT_ATTR = RCPMLTagConstants.ONCLICK_ATTR;		
		
		private Hyperlink fHyperLink;
		protected HyperLinkBridge(Node node, IController controller) {
			super(node, controller,false);			
		}

		protected void construct(Composite parent) {
			String text = DOMUtils.getChildrenAsText(this.getNode());
			fHyperLink = this.getFormToolkit().createHyperlink(parent, text, SWT.WRAP );			
			
			//GridData gd = new GridData();
			//gd.horizontalSpan = 2;
			//this.fHyperLink.setLayoutData( gd );
			this.fHyperLink.setLayoutData( this.constructLayoutData(parent ) );
			
			fHyperLink.addHyperlinkListener(new HyperlinkAdapter() {
				public void linkActivated(HyperlinkEvent e) {
					Node nde = getNode();
					String script = getAttribute( ONCLICK_SCRIPT_ATTR );
					if( script != null ) {
						try {
							getController().getScriptManager().executeScript(script);
						} catch (ScriptException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			
			fHyperLink.addDisposeListener( new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					disposeDataBinding();
				}			
			});
		}

		public Object getPresentation() {
			return fHyperLink;
		}
		
	}
	public HyperLinkTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new HyperLinkBridge(node, this.getController());
	}

}

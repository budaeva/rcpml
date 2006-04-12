package org.rcpml.swt.internal.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.swt.AbstractSWTBridge;
import org.rcpml.swt.SWTUtils;
import org.w3c.dom.Node;

public class CompositeTagFactory extends AbstractBridgeFactory {
	private static class CompositeBridge extends AbstractSWTBridge {
		private Composite fComposite;
		protected CompositeBridge(Node node, IController controller) {
			super(node, controller, true);
		}

		@Override
		protected void construct(Composite parent) {			
			this.fComposite = new Composite( parent, SWT.NULL );
			RCPStylableElement stylable =  (RCPStylableElement)this.getNode();
			this.fComposite.setLayout(SWTUtils.constructLayout( stylable ));
			
			fComposite.addDisposeListener( new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					disposeDataBinding();
				}			
			});
		}
				

		@Override
		public void update() {					
			this.fComposite.layout();
		}

		public Object getPresentation() {
			return this.fComposite;
		}
		
	}
	public CompositeTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new CompositeBridge( node, this.getController() );
	}

}

package org.rcpml.swt;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.ICompositeHolder;
import org.rcpml.core.dom.RCPStylableElement;
import org.w3c.dom.Node;

public abstract class AbstractSWTBridge extends AbstractBridge {
	private DataBindingContext fDatabindingContext;
	protected AbstractSWTBridge(Node node, IController controller,
			boolean visitChilds) {
		super(node, controller, visitChilds);

		IBridge parentBridge = this.getParent();

		if (parentBridge != null) {
			Object presentation = parentBridge.getPresentation();
			if (presentation != null) {
				Composite composite = null;
				if (presentation instanceof Composite) {
					composite = (Composite) presentation;
				} else if (presentation instanceof ICompositeHolder) {
					composite = ((ICompositeHolder) presentation)
							.getComposite();
				}
				if (composite != null) {
					this.construct(composite);
				} else {
					throw new RCPMLException("Composite can't be null");
				}
			} else {
				//throw new RCPMLException("Parent can't be null");
			}
		}
	}

	protected abstract void construct(Composite parent);
	
	protected Object constructLayoutData( Composite parent ) {
		return constructSWTLayoutData(parent, this.getNode());
	}

	public static Object constructSWTLayoutData(Composite parent, Node node ) {
		RCPStylableElement stylable = (RCPStylableElement) node;		
		return SWTUtils.constructLayoutData(stylable, parent );
	}	
	protected DataBindingContext getBindingContext() {
		if( this.fDatabindingContext != null ) {
			return this.fDatabindingContext;
		}
	    this.fDatabindingContext = new DataBindingContext();
	//    this.fDatabindingContext.addObservableFactory(new BeanObservableFactory(this.fDatabindingContext, null, null)); // binding to beans
//	    this.fDatabindingContext.addObservableFactory(new SWTObservableFactory()); // binding to SWT controls
//	    this.fDatabindingContext.addBindSupportFactory(new DefaultBindSupportFactory()); // default converters
//	    this.fDatabindingContext.addBindingFactory(new DefaultBindingFactory()); // default binding behaviour
	    return fDatabindingContext;
	}
	protected void disposeDataBinding() {
		if( this.fDatabindingContext != null ) {
			this.fDatabindingContext.dispose();
		}
	}
}

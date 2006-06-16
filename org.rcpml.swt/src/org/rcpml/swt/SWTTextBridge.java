/**
 * 
 */
package org.rcpml.swt;

import org.eclipse.jface.internal.databinding.provisional.DataBindingContext;
import org.eclipse.jface.internal.databinding.provisional.description.Property;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.rcpml.core.IController;
import org.rcpml.swt.databinding.ElementTextObservable;
import org.w3c.dom.Node;

public class SWTTextBridge extends AbstractSWTBridge {
	private Text fText;
	
	public SWTTextBridge(Node node, IController controller) {
		super(node, controller, false);
	}

	protected Text createText( Composite parent ) {
		return new Text(parent, SWT.BORDER);
	}
	protected void construct(Composite parent) {
		this.fText = this.createText(parent);
		update();
		
		//todo add specs test here.
		DataBindingContext dbc = this.getBindingContext();
		dbc.bind( new Property( this.fText, "text" ), new ElementTextObservable(getNode()), null );
	}	

	public Object getPresentation() {
		return this.fText;
	}

	public void update() {
		this.fText.setLayoutData(this.constructLayout(this.fText.getParent()));
	}
}
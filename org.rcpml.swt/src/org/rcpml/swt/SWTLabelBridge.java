/**
 * 
 */
package org.rcpml.swt;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.rcpml.core.IController;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.dom.RCPStylableElement;
import org.w3c.dom.Node;

public class SWTLabelBridge extends AbstractSWTBridge {
	private static final String SEPARATOR = "separator";
	private Label fLabel;
	public SWTLabelBridge(Node node, IController container) {
		super( node, container, false );			
	}
	public Object getPresentation() {
		return this.fLabel;
	}
	protected void construct( Composite parent ) {
		int style = SWT.NULL;
		
		Value wrapValue = ((RCPStylableElement)this.getNode()).getComputedValue( RCPCSSConstants.LAYOUT_WRAP_INDEX );
		
		if( wrapValue.getStringValue().equals(RCPCSSConstants.TRUE_VALUE)) {
			style |= SWT.WRAP;
		}		
		String separator = getAttribute(SEPARATOR);
		if( separator != null && separator.equals("horizontal")) {
			style |= SWT.SEPARATOR | SWT.HORIZONTAL; 
		}
		this.fLabel = constructLabel( parent, style );		
		update();
		
		this.fLabel.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}			
		});
	}
	protected Label constructLabel( Composite parent, int style ) {
		return new Label(parent, style );
	}
	public void update() {		
		this.fLabel.setLayoutData( constructLayoutData(fLabel.getParent() ));
		this.fLabel.setText( DOMUtils.getChildrenAsText(this.getNode()));		
	}		
}
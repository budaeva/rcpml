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
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.dom.RCPStylableElement;
import org.w3c.dom.Node;

public class SWTLabelBridge extends AbstractSWTBridge {
	private static final String SEPARATOR = RCPMLTagConstants.SEPARATOR_ATTR;
	private static final String TOOLTIP = RCPMLTagConstants.TOOLTIP_ATTR;
	private Label fLabel;
	public SWTLabelBridge(Node node, IController container) {
		super( node, container, false );			
	}
	@Override
	public Object getPresentation() {
		return this.fLabel;
	}
	@Override
	protected void construct( Composite parent ) {
		int style = SWT.NULL;
		
		Value wrapValue = ((RCPStylableElement)this.getNode()).getComputedValue( RCPCSSConstants.LAYOUT_WRAP_INDEX );
		
		if( wrapValue.getStringValue().equals(RCPCSSConstants.TRUE_VALUE)) {
			style |= SWT.WRAP;
		}		
		String separator = getAttribute(SEPARATOR);
		if( separator != null && separator.equals(RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE)) {
			style |= SWT.SEPARATOR | SWT.HORIZONTAL; 
		}
		this.fLabel = constructLabel( parent, style );
		
		this.fLabel.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}			
		});
		
		update();
	}
	protected Label constructLabel( Composite parent, int style ) {
		return new Label(parent, style );
	}
	@Override
	public void update() {		
		if( this.fLabel != null ) {
			this.fLabel.setLayoutData( constructLayoutData(fLabel.getParent() ));
			this.fLabel.setText( DOMUtils.getChildrenAsText(this.getNode()));
			
			String tooltip = getAttribute(TOOLTIP);
			if( tooltip!= null ) {
				this.fLabel.setToolTipText(tooltip);
			}
		}
	}	
	@Override
	public void dispose() {
		if( this.fLabel != null ) {
			this.fLabel.dispose();
			this.fLabel = null;
		}
	}
}
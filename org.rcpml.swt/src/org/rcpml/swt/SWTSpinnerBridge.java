package org.rcpml.swt;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.rcpml.core.IController;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.dom.RCPStylableElement;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 *
 */
public class SWTSpinnerBridge extends AbstractSWTBridge {
	
	private static final String FALSE_VALUE = RCPCSSConstants.FALSE_VALUE;
	private static final String TRUE_VALUE = RCPCSSConstants.TRUE_VALUE;

	private static final String EDITABLE_ID = "editable";
	
	private Spinner fSpinner;
	
	public SWTSpinnerBridge(Node node, IController container) {
		super( node, container, false );			
	}
	
	public Object getPresentation() {
		return this.fSpinner;
	}
	protected void construct( Composite parent ) {
		fSpinner = constructSpinner( parent, getStyle() );
		fSpinner.setMinimum(Integer.MIN_VALUE);
		fSpinner.setMaximum(Integer.MAX_VALUE);
		fSpinner.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}
		});
		
		update();
	}
	
	protected int getStyle() {
		int style = SWT.BORDER;
		
		Value wrapValue = ((RCPStylableElement)this.getNode()).getComputedValue( RCPCSSConstants.LAYOUT_WRAP_INDEX );
		if( wrapValue.getStringValue().equals(TRUE_VALUE) )
			style |= SWT.WRAP;
		
		String editable = getAttribute(EDITABLE_ID);
		if( editable != null && editable.equals(FALSE_VALUE) )
			style |= SWT.READ_ONLY;

		return style;
	}
	
	protected Spinner constructSpinner( Composite parent, int style ) {
		return new Spinner(parent, style );
	}
	public void update() {		
		if( this.fSpinner != null ) {
			this.fSpinner.setLayoutData( constructLayoutData(fSpinner.getParent() ));
			int value = 0;
			try {
				value = Integer.parseInt(DOMUtils.getChildrenAsText(this.getNode()));
			}
			catch (Exception e) {
			}
			this.fSpinner.setSelection(value);
		}
	}
	public void dispose() {
		if( this.fSpinner != null ) {
			this.fSpinner.dispose();
			this.fSpinner = null;
		}
	}
}
/**
 * 
 */
package org.rcpml.swt;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.jface.internal.databinding.provisional.DataBindingContext;
import org.eclipse.jface.internal.databinding.provisional.description.Property;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.rcpml.core.IController;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.swt.databinding.ElementTextObservable;
import org.w3c.dom.Node;

public class SWTTextBridge extends AbstractSWTBridge {
	private static final String EDITABLE_ID = "editable";
	protected Text fText;
	
	public SWTTextBridge(Node node, IController controller) {
		super(node, controller, false);
	}

	protected Text createText( Composite parent ) {
		return new Text(parent, getStyle());
	}
	protected void construct(Composite parent) {
		this.fText = this.createText(parent);
		update();
		
		//todo add specs test here.
		DataBindingContext dbc = this.getBindingContext();
		dbc.bind( new Property( this.fText, "text" ), new ElementTextObservable(getNode()), null );
	}	
	
	protected int getStyle() {
		int style = 0;		
		RCPStylableElement stylable = (RCPStylableElement)getNode();
				
		Value borderValue = stylable.getComputedValue(RCPCSSConstants.LAYOUT_BORDER_INDEX);
		String border = borderValue.getStringValue();
		if( border.equals(RCPCSSConstants.LAYOUT_BORDER_VALUE)) {
			return style | SWT.BORDER;
		}
		else if( border.equals(RCPCSSConstants.LAYOUT_BORDER_SINGLE_VALUE)) {
			return style | SWT.SINGLE | SWT.BORDER;
		}
		return style;
	}

	public Object getPresentation() {
		return this.fText;
	}

	public void update() {
		this.fText.setLayoutData(this.constructLayoutData(this.fText.getParent()));
		
		String editable = "true";
		editable = getAttribute(EDITABLE_ID);
		if( editable != null ) {
			if( editable.equals("false") ) {
				this.fText.setEditable(false);
			}
			else {
				this.fText.setEditable(true);
			}
		}
	}
}
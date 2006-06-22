package org.rcpml.core.internal.css.managers;

import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.Value;
import org.rcpml.core.css.RCPCSSConstants;
import org.w3c.dom.css.CSSPrimitiveValue;

public class LayoutAlignValueManager extends IdentifierManager {
	private String fPropertyName;
	
	public final static Value LEFT_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_ALIGN_LEFT_VALUE );
	public final static Value CENTER_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_ALIGN_CENTER_VALUE );
	public final static Value RIGHT_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_ALIGN_RIGHT_VALUE );
	
	private Value fDefaultValue;
	
	/**
	 * The identifier values.
	 */
	protected final static StringMap values = new StringMap();
	static {
		values.put(RCPCSSConstants.LAYOUT_ALIGN_LEFT_VALUE, LEFT_VALUE );		
		values.put(RCPCSSConstants.LAYOUT_ALIGN_CENTER_VALUE, CENTER_VALUE );
		values.put(RCPCSSConstants.LAYOUT_ALIGN_RIGHT_VALUE, RIGHT_VALUE );
	}
	
	public LayoutAlignValueManager(String property, Value defaultValue ) {
		this.fPropertyName = property;
		this.fDefaultValue = defaultValue;
	}

	public String getPropertyName() {
		return this.fPropertyName;
	}

	public boolean isInheritedProperty() {
		return false;
	}

	public Value getDefaultValue() {
		return fDefaultValue;
	}	

	public StringMap getIdentifiers() {
		return values;
	}
}

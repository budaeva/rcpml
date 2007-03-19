package org.rcpml.core.internal.css.managers;

import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.Value;
import org.rcpml.core.css.RCPCSSConstants;
import org.w3c.dom.css.CSSPrimitiveValue;

public class BorderValueManager extends IdentifierManager {
	
	final static Value SINGNE_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_BORDER_SINGLE_VALUE );
	final static Value DOUBLE_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_BORDER_DOUBLE_VALUE );
	final static Value NONE_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_BORDER_NONE_VALUE );
	
	final static Value DEFAULT_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_BORDER_VALUE );	

	/**
	 * The identifier values.
	 */
	protected final static StringMap values = new StringMap();
	static {
		values.put(RCPCSSConstants.LAYOUT_BORDER_SINGLE_VALUE, SINGNE_VALUE );				
		values.put(RCPCSSConstants.LAYOUT_BORDER_DOUBLE_VALUE, DOUBLE_VALUE );
		values.put(RCPCSSConstants.LAYOUT_BORDER_NONE_VALUE, NONE_VALUE );
		values.put(RCPCSSConstants.LAYOUT_BORDER_VALUE, DEFAULT_VALUE );
	}

	public StringMap getIdentifiers() {
		return values;
	}

	public String getPropertyName() {
		return RCPCSSConstants.CSS_LAYOUT_BORDER_PROPERTY;
	}

	public boolean isInheritedProperty() {
		return false;
	}

	public Value getDefaultValue() {
		return DEFAULT_VALUE;
	}

	public int getPropertyType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isAdditiveProperty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAnimatableProperty() {
		// TODO Auto-generated method stub
		return false;
	}
}

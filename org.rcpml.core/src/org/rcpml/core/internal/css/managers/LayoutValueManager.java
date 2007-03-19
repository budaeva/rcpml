package org.rcpml.core.internal.css.managers;

import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.Value;
import org.rcpml.core.css.RCPCSSConstants;
import org.w3c.dom.css.CSSPrimitiveValue;

public class LayoutValueManager extends IdentifierManager {
	
	final static Value GRID_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_GRID_VALUE );
	final static Value ROW_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_ROW_VALUE );
	final static Value FILL_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_FILL_VALUE );
	final static Value WRAP_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_WRAP_VALUE );
	final static Value COLUMN_VALUE = new StringValue(CSSPrimitiveValue.CSS_IDENT, 	RCPCSSConstants.LAYOUT_COLUMN_VALUE );

	/**
	 * The identifier values.
	 */
	protected final static StringMap values = new StringMap();
	static {
		values.put(RCPCSSConstants.LAYOUT_GRID_VALUE, GRID_VALUE );		
		values.put(RCPCSSConstants.LAYOUT_ROW_VALUE, ROW_VALUE );
		values.put(RCPCSSConstants.LAYOUT_FILL_VALUE, FILL_VALUE );
		values.put(RCPCSSConstants.LAYOUT_WRAP_VALUE, WRAP_VALUE );
		values.put(RCPCSSConstants.LAYOUT_COLUMN_VALUE, COLUMN_VALUE );
	}

	public StringMap getIdentifiers() {
		return values;
	}

	public String getPropertyName() {
		return RCPCSSConstants.CSS_LAYOUT_PROPERTY;
	}

	public boolean isInheritedProperty() {
		return false;
	}

	public Value getDefaultValue() {
		return GRID_VALUE;
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

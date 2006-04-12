package org.rcpml.core.internal.css.managers;

import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.ValueManager;
import org.rcpml.core.css.RCPCSSConstants;
import org.w3c.dom.css.CSSPrimitiveValue;

public class TrueFalseValueManager extends IdentifierManager implements
		ValueManager {
	
	final static Value TRUE_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT, RCPCSSConstants.TRUE_VALUE);
	final static Value FALSE_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT, RCPCSSConstants.TRUE_VALUE);
	final static Value NONE_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT, RCPCSSConstants.NONE_VALUE);
	
	String fPropertyName;
	
	static StringMap values = new StringMap();
	static {
		values.put( RCPCSSConstants.TRUE_VALUE, TRUE_VALUE );
		values.put( RCPCSSConstants.FALSE_VALUE, FALSE_VALUE );
		values.put( RCPCSSConstants.NONE_VALUE, NONE_VALUE );
	}
	
	public TrueFalseValueManager(String property ) {
		this.fPropertyName = property;
	}

	@Override
	public StringMap getIdentifiers() {
		return values;
	}

	@Override
	public String getPropertyName() {
		return this.fPropertyName;
	}

	public boolean isInheritedProperty() {
		return true;
	}

	public Value getDefaultValue() {
		return NONE_VALUE;
	}

}

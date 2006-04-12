package org.rcpml.core.internal.css.managers;

import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.Value;
import org.rcpml.core.css.RCPCSSConstants;
import org.w3c.dom.css.CSSPrimitiveValue;

public class LayoutDirectionValueManager extends IdentifierManager {

	private String fPropertyName;

	final static Value NONE_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT, RCPCSSConstants.LAYOUT_NONE_VALUE);

	final static Value HORIZONTAL_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT,
			RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE);

	final static Value VERTICAL_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT, RCPCSSConstants.LAYOUT_VERTICAL_VALUE);

	final static Value BOTH_VALUE = new StringValue(
			CSSPrimitiveValue.CSS_IDENT, RCPCSSConstants.LAYOUT_BOTH_VALUE);

	/**
	 * The identifier values.
	 */
	protected final static StringMap values = new StringMap();
	static {
		values.put(RCPCSSConstants.LAYOUT_NONE_VALUE, NONE_VALUE);
		values.put(RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE, HORIZONTAL_VALUE);
		values.put(RCPCSSConstants.LAYOUT_VERTICAL_VALUE, VERTICAL_VALUE);
		values.put(RCPCSSConstants.LAYOUT_BOTH_VALUE, BOTH_VALUE);
	}

	public LayoutDirectionValueManager(String property) {
		this.fPropertyName = property;
	}

	@Override
	public StringMap getIdentifiers() {
		return this.values;
	}

	@Override
	public String getPropertyName() {
		return this.fPropertyName;
	}

	public boolean isInheritedProperty() {
		return false;
	}

	public Value getDefaultValue() {
		return this.NONE_VALUE;
	}

}

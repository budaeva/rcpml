package org.rcpml.core.internal.css.managers;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.AbstractValueManager;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.ValueManager;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;

public class NumberValueManager extends AbstractValueManager implements
		ValueManager {
	private Value fDefaultValue;

	private String fPropertyName;

	private boolean fInherited;

	public NumberValueManager(String propertyName, int defaultValue,
			boolean inherited) {
		this.fPropertyName = propertyName;
		this.fDefaultValue = new FloatValue(CSSPrimitiveValue.CSS_NUMBER,
				defaultValue);
		this.fInherited = inherited;
	}

	@Override
	public String getPropertyName() {
		return this.fPropertyName;
	}

	public boolean isInheritedProperty() {
		return this.fInherited;
	}

	public Value getDefaultValue() {
		return this.fDefaultValue;
	}

	public Value createValue(LexicalUnit lu, CSSEngine engine)
			throws DOMException {
		switch (lu.getLexicalUnitType()) {
		case LexicalUnit.SAC_EM:
			return new FloatValue(CSSPrimitiveValue.CSS_EMS, lu.getFloatValue());

		case LexicalUnit.SAC_EX:
			return new FloatValue(CSSPrimitiveValue.CSS_EXS, lu.getFloatValue());

		case LexicalUnit.SAC_PIXEL:
			return new FloatValue(CSSPrimitiveValue.CSS_PX, lu.getFloatValue());

		case LexicalUnit.SAC_CENTIMETER:
			return new FloatValue(CSSPrimitiveValue.CSS_CM, lu.getFloatValue());

		case LexicalUnit.SAC_MILLIMETER:
			return new FloatValue(CSSPrimitiveValue.CSS_MM, lu.getFloatValue());

		case LexicalUnit.SAC_INCH:
			return new FloatValue(CSSPrimitiveValue.CSS_IN, lu.getFloatValue());

		case LexicalUnit.SAC_POINT:
			return new FloatValue(CSSPrimitiveValue.CSS_PT, lu.getFloatValue());

		case LexicalUnit.SAC_PICA:
			return new FloatValue(CSSPrimitiveValue.CSS_PC, lu.getFloatValue());

		case LexicalUnit.SAC_INTEGER:
			return new FloatValue(CSSPrimitiveValue.CSS_NUMBER, lu
					.getIntegerValue());

		case LexicalUnit.SAC_REAL:
			return new FloatValue(CSSPrimitiveValue.CSS_NUMBER, lu
					.getFloatValue());

		case LexicalUnit.SAC_PERCENTAGE:
			return new FloatValue(CSSPrimitiveValue.CSS_PERCENTAGE, lu
					.getFloatValue());
		}
		throw createInvalidLexicalUnitDOMException(lu.getLexicalUnitType());
	}
}

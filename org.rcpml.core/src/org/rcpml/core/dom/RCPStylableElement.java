package org.rcpml.core.dom;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.Value;

public interface RCPStylableElement {
	CSSEngine getCSSEngine();
	Value getComputedValue( int index );
}

package org.rcpml.core.internal.css.dom;

import org.apache.batik.css.dom.CSSOMComputedStyle;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStylableElement;

public class CSSOMRCPComputedStyle extends CSSOMComputedStyle {
	public CSSOMRCPComputedStyle(CSSEngine e, CSSStylableElement elt,
			String pseudoElt) {
		super(e, elt, pseudoElt);
	}

}

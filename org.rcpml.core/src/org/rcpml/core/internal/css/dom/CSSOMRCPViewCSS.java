package org.rcpml.core.internal.css.dom;

import org.apache.batik.css.dom.CSSOMViewCSS;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;

public class CSSOMRCPViewCSS extends CSSOMViewCSS {

	public CSSOMRCPViewCSS(CSSEngine engine) {
		super(engine);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <b>DOM</b>: Implements {@link
	 * org.w3c.dom.css.ViewCSS#getComputedStyle(Element,String)}.
	 */
	public CSSStyleDeclaration getComputedStyle(Element elt, String pseudoElt) {
		if (elt instanceof CSSStylableElement) {
			return new CSSOMRCPComputedStyle(cssEngine,
					(CSSStylableElement) elt, pseudoElt);
		}
		return null;
	}

}

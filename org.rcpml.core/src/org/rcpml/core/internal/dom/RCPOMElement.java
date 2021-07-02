package org.rcpml.core.internal.dom;

import org.apache.batik.constants.XMLConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.StyleDeclarationProvider;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.GenericElementNS;
import org.apache.batik.util.ParsedURL;
import org.rcpml.core.dom.RCPStylableElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RCPOMElement extends GenericElementNS implements
		CSSStylableElement, RCPStylableElement {
	private static final long serialVersionUID = 1L;

	//private StyleMap fStyleMap;

	public RCPOMElement(String nsURI, String name, AbstractDocument owner) {
		super(nsURI, name, owner);
		nodeName = name;
	}

	public StyleMap getComputedStyleMap(String arg0) {
		RCPOMDocument document = (RCPOMDocument)this.getOwnerDocument();
		return document.getStyleMapForElement(this);
	}

	public void setComputedStyleMap(String pseudo, StyleMap styleMap) {
		RCPOMDocument document = (RCPOMDocument)this.getOwnerDocument();
		document.putStyleMapForElement(this, styleMap );
	}

	public String getXMLId() {
		return getAttributeNS(null, "id");
	}

	public String getCSSClass() {
		return getAttributeNS(null, "class");
	}

	public ParsedURL getCSSBase() {
			if (getXblBoundElement() != null) {
				return null;
			}
			String bu = getBaseURI();
			if (bu == null) {
				return null;
			}
			return new ParsedURL(bu);
	}


	public boolean isPseudoInstanceOf(String pseudoClass) {
		if (pseudoClass.equals("first-child")) {
			Node n = getPreviousSibling();
			while (n != null && n.getNodeType() != ELEMENT_NODE) {
				n = n.getPreviousSibling();
			}
			return n == null;
		}
		return false;
	}

	public CSSEngine getCSSEngine() {
		return ((RCPOMDocument)this.getOwnerDocument()).getCSSEngine();
	}

	public Value getComputedValue(int index) {		
		CSSEngine engine = this.getCSSEngine();
		return engine.getComputedStyle( this, null, index );
	}

	public StyleDeclarationProvider getOverrideStyleDeclarationProvider() {
		return null;
	}
	
    
    
}

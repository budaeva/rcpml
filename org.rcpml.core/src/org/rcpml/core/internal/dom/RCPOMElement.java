package org.rcpml.core.internal.dom;

import java.net.MalformedURLException;
import java.net.URL;

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
//		try {
			if (getXblBoundElement() != null) {
				return null;
			}
			String bu = getBaseURI();
			if (bu == null) {
				return null;
			}
			return new ParsedURL(bu);
//		} catch (MalformedURLException e) {
//			// !!! TODO
//			e.printStackTrace();
//			throw new InternalError();
//		}
	}

	public Element getXblBoundElement() {
		// TODO Auto-generated method stub
		return null;
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
	
    /**
     * <b>DOM</b>: Implements {@link org.w3c.dom.Node#getBaseURI()}.
     */
    public String getBaseURI() {
        return getCascadedXMLBase(this);
    }
    
    /**
     * Returns the xml:base attribute value of the given element,
     * resolving any dependency on parent bases if needed.
     */
    protected String getCascadedXMLBase(Node node) {
        String base = null;
        Node n = node.getParentNode();
        while (n != null) {
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                base = getCascadedXMLBase(n);
                break;
            }
            n = n.getParentNode();
        }
        if (base == null) {
            AbstractDocument doc;
            if (node.getNodeType() == Node.DOCUMENT_NODE) {
                doc = (AbstractDocument) node;
            } else {
                doc = (AbstractDocument) node.getOwnerDocument();
            }
            base = doc.getDocumentURI();
        }
        while (node != null && node.getNodeType() != Node.ELEMENT_NODE) {
            node = node.getParentNode();
        }
        if (node == null) {
            return base;
        }
        Element e = (Element) node;
        Attr attr = e.getAttributeNodeNS(XMLConstants.XML_NAMESPACE_URI,
                                         XMLConstants.XML_BASE_ATTRIBUTE);
        if (attr != null) {
            if (base == null) {
                base = attr.getNodeValue();
            } else {
                base = new ParsedURL(base, attr.getNodeValue()).toString();
            }
        }
        return base;
    }
}

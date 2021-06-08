package org.rcpml.core.internal.dom;

import java.net.URL;

import org.apache.batik.css.engine.CSSContext;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.parser.ExtendedParser;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.AbstractStylableDocument;
import org.apache.batik.dom.ExtensibleDOMImplementation;
import org.apache.batik.dom.GenericDocumentType;
import org.apache.batik.dom.util.CSSStyleDeclarationFactory;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.util.ParsedURL;
import org.rcpml.core.internal.css.RCPCSSEngine;
import org.rcpml.core.internal.css.dom.CSSOMRCPViewCSS;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.stylesheets.StyleSheet;

public class RCPDOMImplementation extends ExtensibleDOMImplementation implements
		CSSStyleDeclarationFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5824136402621437612L;
	private final static RCPDOMImplementation IMPLEMENTATION = new RCPDOMImplementation();
	private static final Object RCPML_CORE_NAMESPACE = "http://rcpml.org/core";
	private static final Object RCPML_CORE_STYLE_ELEMENT_NAME = "style";

	public static DOMImplementation getDOMImplementation() {
		return IMPLEMENTATION;
	}
	private RCPDOMImplementation() {
		super();
		//registerFeature("CSS",            "2.0");
        registerFeature("StyleSheets",    "2.0");
	}

	public CSSEngine createCSSEngine(AbstractStylableDocument doc,
			CSSContext ctx, ExtendedParser ep, ValueManager[] vms,
			ShorthandManager[] sms) {
		URL durl = ((RCPOMDocument) doc).getURLObject();
		return new RCPCSSEngine(doc, new ParsedURL(durl), ep, vms, sms, ctx);
	}

	public ViewCSS createViewCSS(AbstractStylableDocument doc) {
		return new CSSOMRCPViewCSS(doc.getCSSEngine());
	}

	public CSSStyleSheet createCSSStyleSheet(String arg0, String arg1)
			throws DOMException {
		throw new InternalError("Not implemented");
	}

	public StyleSheet createStyleSheet(Node arg0, HashTable arg1) {
		throw new InternalError("Not implemented");
	}

	public Document createDocument(String namespaceURI, String qualifiedName,
			DocumentType doctype) throws DOMException {
		Document result = new RCPOMDocument(doctype, this);
		if (qualifiedName != null)
			result.appendChild(result.createElementNS(namespaceURI,
					qualifiedName));
		return result;
	}
	
    /**
     * Implements the behavior of Document.createElementNS() for this
     * DOM implementation.
     */
    public Element createElementNS(AbstractDocument document,
                                   String           namespaceURI,
                                   String           qualifiedName) {
        
    	if( RCPML_CORE_NAMESPACE.equals(namespaceURI)) {
    		String name = DOMUtilities.getLocalName(qualifiedName);
    		if( RCPML_CORE_STYLE_ELEMENT_NAME.equals( name ) ) {
    			return new RCPOMStyleElement(namespaceURI, qualifiedName, document );
    		}
    	}
        return new RCPOMElement( namespaceURI, qualifiedName, document );        

        //return super.createElementNS(document, namespaceURI, qualifiedName);
    }

	/**
	 * <b>DOM</b>: Implements {@link
	 * DOMImplementation#createDocumentType(String,String,String)}.
	 */
	public DocumentType createDocumentType(String qualifiedName,
			String publicId, String systemId) {
		return new GenericDocumentType(qualifiedName, publicId, systemId);
	}

	public CSSStyleDeclaration createCSSStyleDeclaration() {
		throw new InternalError("Not implemented");
	}	
	/**
     * <b>DOM</b>: Implements
     * {@link org.w3c.dom.DOMImplementation#getFeature(String,String)}.
     * No compound document support, so just return this DOMImlpementation
     * where appropriate.
     */
    public Object getFeature(String feature, String version) {
        if (hasFeature(feature, version)) {
            return this;
        }
        return null;
    }
}

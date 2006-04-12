package org.rcpml.core.internal.dom;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStyleSheetNode;
import org.apache.batik.css.engine.StyleSheet;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.StyleSheetFactory;
import org.apache.batik.dom.StyleSheetProcessingInstruction;
import org.apache.batik.dom.util.HashTable;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class RCPStyleSheetProcessingInstruction extends
		StyleSheetProcessingInstruction implements CSSStyleSheetNode {
	
	private static final long serialVersionUID = 2355600426764441433L;
	/**
	 * The style-sheet.
	 */
	protected StyleSheet fStyleSheet;

	public RCPStyleSheetProcessingInstruction() {

	}

	/**
	 * Creates a new ProcessingInstruction object.
	 */
	public RCPStyleSheetProcessingInstruction(String data,
			AbstractDocument owner, StyleSheetFactory f) {
		super(data, owner, f);
	}

	/**
     * Returns the associated style-sheet.
     */
    public StyleSheet getCSSStyleSheet() {
        if (fStyleSheet == null) {
            HashTable attrs = getPseudoAttributes();
            String type = (String)attrs.get("type");

            if ("text/css".equals(type)) {
                String title     = (String)attrs.get("title");
                String media     = (String)attrs.get("media");
                String href      = (String)attrs.get("href");
                String alternate = (String)attrs.get("alternate");
                RCPOMDocument doc = (RCPOMDocument)getOwnerDocument();
                URL durl = doc.getURLObject();
                URL burl = durl;
                try {
                    burl = new URL(durl, href);
                } catch (Exception ex) {
                }
                CSSEngine e = doc.getCSSEngine();
                
                fStyleSheet = e.parseStyleSheet
                    (burl, media);
                fStyleSheet.setAlternate("yes".equals(alternate));
                fStyleSheet.setTitle(title);
            }
        }
        return fStyleSheet;
    }
	/**
     * Returns the URI of the referenced stylesheet.
     */
    public String getStyleSheetURI() {
    	RCPOMDocument svgDoc;
        svgDoc = (RCPOMDocument)getOwnerDocument();
        URL url = svgDoc.getURLObject();
        String href = (String)getPseudoAttributes().get("href");
        if (url != null) {
            try {
                return new URL(url, href).toString();
            } catch (MalformedURLException e) {
            }
        }
        return href;
    }
    /**
     * <b>DOM</b>: Implements {@link
     * org.w3c.dom.ProcessingInstruction#setData(String)}.
     */
    public void setData(String data) throws DOMException {
    	super.setData(data);
        fStyleSheet = null;
    }

    /**
     * Returns a new uninitialized instance of this object's class.
     */
    protected Node newNode() {
        return new RCPStyleSheetProcessingInstruction();
    }
}

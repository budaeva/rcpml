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

    /**
     * The style-sheet.
     */
    protected StyleSheet styleSheet;
	
	protected RCPStyleSheetProcessingInstruction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RCPStyleSheetProcessingInstruction(String data,
			AbstractDocument owner, StyleSheetFactory f) {
		super(data, owner, f);
		// TODO Auto-generated constructor stub
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
     * Returns the associated style-sheet.
     */
    public StyleSheet getCSSStyleSheet() {
        if (styleSheet == null) {
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
                
                styleSheet = e.parseStyleSheet
                    (burl, media);
                styleSheet.setAlternate("yes".equals(alternate));
                styleSheet.setTitle(title);
            }
        }
        return styleSheet;
    }

    /**
     * <b>DOM</b>: Implements {@link
     * org.w3c.dom.ProcessingInstruction#setData(String)}.
     */
    public void setData(String data) throws DOMException {
	super.setData(data);
        styleSheet = null;
    }

    /**
     * Returns a new uninitialized instance of this object's class.
     */
    protected Node newNode() {
        return new RCPStyleSheetProcessingInstruction();
    }
	
}

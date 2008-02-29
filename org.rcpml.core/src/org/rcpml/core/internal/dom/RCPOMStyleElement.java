package org.rcpml.core.internal.dom;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStyleSheetNode;
import org.apache.batik.css.engine.StyleSheet;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.XMLConstants;
import org.rcpml.core.IController;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.stylesheets.LinkStyle;

public class RCPOMStyleElement extends RCPOMElement implements
		CSSStyleSheetNode, LinkStyle {
	
	private static final long serialVersionUID = -438785343621551661L;
	
	private static final String TYPE_ATTRIBUTE = "type";
	private static final String MEDIA_ATTRIBUTE = "media";
    

    /**
     * The style sheet.
     */
    //protected transient org.w3c.dom.stylesheets.StyleSheet fSheet;
    
    /**
     * The DOM CSS style-sheet.
     */
    protected transient StyleSheet fStyleSheet;

	private EventListener domCharacterDataModifiedListener = new EventListener() {
		public void handleEvent(Event arg0) {
			fStyleSheet = null;		
			RCPOMDocument document = (RCPOMDocument)getOwnerDocument();
			document.clearStylesMap();
		}		
	};


    /**
     * Creates a new SVGOMStyleElement object.
     * @param prefix The namespace prefix.
     * @param owner The owner document.
     */
    public RCPOMStyleElement(String namespaceURI, String nodeName, AbstractDocument owner) {
        super( namespaceURI, nodeName, owner);
        setEventHandler();
    }

    
	private void setEventHandler() {
		addEventListener(IController.DOMSUBTREE_MODIFIED, domCharacterDataModifiedListener, true);
		addEventListener(IController.DOMNODE_INSERTED, domCharacterDataModifiedListener, true);
		addEventListener(IController.DOMNODE_REMOVED, domCharacterDataModifiedListener, true);		
		addEventListener(IController.DOMATTR_MODIFIED, domCharacterDataModifiedListener, true);		
	}


	public StyleSheet getCSSStyleSheet() {
		 if (fStyleSheet == null) {
	            if (getType().equals("text/css")) {
	            	RCPOMDocument doc = (RCPOMDocument)getOwnerDocument();
	                CSSEngine e = doc.getCSSEngine();
	                String text = "";
	                Node n = getFirstChild();
	                if (n != null) {
	                    StringBuffer sb = new StringBuffer();
	                    while (n != null) {
	                        if (n.getNodeType() == Node.CDATA_SECTION_NODE
	                            || n.getNodeType() == Node.TEXT_NODE)
	                            sb.append(n.getNodeValue());
	                        n = n.getNextSibling();
	                    }
	                    text = sb.toString();
	                }
	                URL burl = null;
	                try {
	                    String bu = getBaseURI();
	                    if (bu != null) {
	                        burl = new URL(bu);
	                    }
	                } catch (MalformedURLException ex) {
	                    // !!! TODO
	                    ex.printStackTrace();
	                    throw new InternalError();
	                }
	                String  media = getAttributeNS(null, MEDIA_ATTRIBUTE);
	                fStyleSheet = e.parseStyleSheet(text, burl, media);
	                
	                addEventListenerNS(XMLConstants.XML_EVENTS_NAMESPACE_URI,
	                                   "DOMCharacterDataModified",
	                                   domCharacterDataModifiedListener,
	                                   false,
	                                   null);
	            }
	        }
	        return fStyleSheet;
	}


	public org.w3c.dom.stylesheets.StyleSheet getSheet() {
		throw new RuntimeException(" !!! Not implemented.");
	}
	private String getType() {		
	       return getAttributeNS(null, TYPE_ATTRIBUTE );
	}
}

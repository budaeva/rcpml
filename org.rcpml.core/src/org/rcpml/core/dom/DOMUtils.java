package org.rcpml.core.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMUtils {

	public static String getAttribute(Node node, String attrName) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			return element.getAttribute(attrName);
		}
		return null;
	}

	public static final void setChildrenText( Node node, String text ) {
		Document doc = node.getOwnerDocument();		
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n.getNodeType() == Node.TEXT_NODE) {
				node.removeChild(n);
			}
		}
		
		Node textNode = doc.createTextNode(text);
		node.appendChild(textNode);		
	}

	public static void setAttribute(Node node, String attrName, String value ) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			element.setAttribute(attrName, value);
		}		
	}

	public static final String getChildrenAsText(Node node) {
		StringBuffer text = new StringBuffer();
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n.getNodeType() == Node.TEXT_NODE)
				text.append(n.getNodeValue());
		}
		String stext = text.toString();
		if (stext != null) {
			return stext.trim();
		}
		return stext;
	}
}

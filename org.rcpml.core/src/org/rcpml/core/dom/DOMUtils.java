package org.rcpml.core.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		List nodes = new ArrayList();
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n.getNodeType() == Node.TEXT_NODE) {
				nodes.add(n);
			}
		}
		Node textNode = doc.createTextNode(text);
		if (nodes.size() == 1) {
			node.replaceChild(textNode, (Node)nodes.get(0));
		}
		else {
			Iterator it = nodes.iterator();
			while (it.hasNext()) {
				Node n = (Node) it.next();
				node.removeChild(n);
			}
			node.appendChild(textNode);
		}
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

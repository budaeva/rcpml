package org.rcpml.core;

import org.rcpml.core.internal.Controller;

import org.w3c.dom.Document;

public class RCPML {	
	
	public static Object renderDocument(Document document) {
		return renderDocument(document, null);
	}

	public static Object renderDocument(Document document, ClassLoader loader) {
		try {
			return new Controller(loader).renderNode(document, null);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

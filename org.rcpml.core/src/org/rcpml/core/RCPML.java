package org.rcpml.core;

import org.osgi.framework.Bundle;
import org.rcpml.core.internal.Controller;

import org.w3c.dom.Document;

public class RCPML {	
	
	public static Object renderDocument(Document document) {
		return renderDocument(document, null);
	}

	public static Object renderDocument(Document document, Bundle provider) {
		try {
			return new Controller(provider).renderNode(document, null);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

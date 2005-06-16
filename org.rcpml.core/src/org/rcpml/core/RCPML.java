package org.rcpml.core;

import org.rcpml.core.internal.Controller;

import org.w3c.dom.Document;

public class RCPML {	
	
	public static Object renderDocument(Document document) {
		return new Controller().renderNode(document, null);
	}
	
}

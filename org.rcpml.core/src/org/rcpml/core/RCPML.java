package org.rcpml.core;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.rcpml.core.internal.Controller;
import org.w3c.dom.Document;

public class RCPML {
	public static Object renderDocument(Document document) {
		return renderDocument(document, null);
	}

	public static Object renderDocument(Document document, Bundle provider) {
		IController bridgeContainer = new Controller(document, provider);		
		return bridgeContainer.getPresentation();
	}	
}

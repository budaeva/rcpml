package org.rcpml.core;

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
	
	public static IRCPMLConstructor createConstructor(Document document, Bundle provider ) {
		IController bridgeContainer = new Controller(document, provider, true );
		IRCPMLConstructor constructor = bridgeContainer.getRootBridge();
		if( constructor != null ) {
			return constructor;
		}
		throw new RuntimeException("RCPML: Could not create constructor from element");
	}
	public static IRCPMLConstructor createConstructor(Document document ) {
		return createConstructor(document, null);
	}
}

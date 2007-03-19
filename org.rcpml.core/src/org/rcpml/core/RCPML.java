package org.rcpml.core;

import org.eclipse.core.runtime.CoreException;
import org.rcpml.core.internal.Controller;
import org.rcpml.core.internal.RCPMLImpl;
import org.w3c.dom.Document;

public class RCPML {
	public static Object renderDocument(Document document) {
		IController bridgeContainer = new Controller(document);
		return bridgeContainer.getPresentation();
	}

	public static IRCPMLConstructor createConstructor(Document document) {
		IController bridgeContainer = new Controller(document, true);
		IRCPMLConstructor constructor = bridgeContainer.getRootBridge();
		if (constructor != null) {
			return constructor;
		}
		throw new RuntimeException(
				"RCPML: Could not create constructor from element");
	}

	public static Object renderURI(String uri) throws CoreException {
		Document doc = RCPMLImpl.getDocument(uri);
		return renderDocument(doc);
	}
}

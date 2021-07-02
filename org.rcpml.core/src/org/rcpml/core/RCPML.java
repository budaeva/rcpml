package org.rcpml.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.rcpml.core.bridge.ICompositeHolder;
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
//		throw new RuntimeException(
//				"RCPML: Could not create constructor from element");
		return null;
	}

	public static Object renderURI(String uri) throws CoreException {
		Document doc = RCPMLImpl.getDocument(uri);
		return renderDocument(doc);
	}

	public static IController create(Document document) {
		return new Controller(document);
	}
	public static IController createWithConstructor(Document document) {
		return new Controller(document, true);
	}
	
	public static Composite createComposite(Document doc, Composite parent) {
		Object presentation = RCPML.renderDocument(doc);
		Composite composite = getComposite(presentation, parent);
		composite.setParent(parent);
		return composite;
	}
	
	private static Composite getComposite(Object presentation, Composite parent) {
		if (presentation instanceof ViewPart) {
			ViewPart vp = (ViewPart) presentation;
			vp.createPartControl(parent);
		}
		Composite composite = null;
		if (presentation instanceof Composite) {
			composite = (Composite) presentation;
		} else if (presentation instanceof ICompositeHolder) {
			composite = ((ICompositeHolder) presentation).getComposite();
		}
		
		return composite;
	}

}

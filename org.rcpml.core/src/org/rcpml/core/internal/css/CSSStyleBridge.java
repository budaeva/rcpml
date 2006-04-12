package org.rcpml.core.internal.css;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStylableElement;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.internal.dom.RCPOMDocument;
import org.w3c.dom.Node;


//Used to load external style data.
public class CSSStyleBridge extends AbstractBridge {	
	protected CSSStyleBridge(Node node, IController controller) {
		super(node, controller, false );	
		RCPOMDocument document = (RCPOMDocument)controller.getDocument();
		CSSEngine engine = document.getCSSEngine();
		
		if( !( node instanceof CSSStylableElement ) ) {
			throw new RCPMLException("Not Stylable element");
		}
	}

	public Object getPresentation() {
		return null;
	}
	
}

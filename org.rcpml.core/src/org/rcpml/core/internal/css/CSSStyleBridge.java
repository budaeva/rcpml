package org.rcpml.core.internal.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.batik.css.engine.CSSStylableElement;
import org.eclipse.core.runtime.CoreException;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.internal.Controller;
import org.rcpml.core.internal.contentprovider.ContentProviderManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//Used to load external style data.
public class CSSStyleBridge extends AbstractBridge {
	private static final String SRC = "src";

	private String fOldSrc;

	protected CSSStyleBridge(Node node, IController controller) {
		super(node, controller, false);
		// RCPOMDocument document = (RCPOMDocument)controller.getDocument();
		// CSSEngine engine = document.getCSSEngine();

		if (!(node instanceof CSSStylableElement)) {
			throw new RCPMLException("Not Stylable element");
		}
		update();
	}

	public void update() {
		String scriptSource = getAttribute(SRC);
		if (scriptSource == null || scriptSource.length() == 0) {
			return;
		}
		if (fOldSrc != null && fOldSrc.equals(scriptSource)) {
			return;
		}
		fOldSrc = scriptSource;
		getController().requireFullUpdate();

		ContentProviderManager provider = ContentProviderManager.getInstance();
		String content = "";
		try {
			InputStream is = provider.getStream(scriptSource);
			if (is != null) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				try {
					String line = br.readLine();
					while (line != null) {
						content += line;
						line = br.readLine();
					}
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}
		} catch (CoreException ex) {
			ex.printStackTrace();
		}
		if (content.length() > 0) {
			Document doc = getNode().getOwnerDocument();
			Node textNode = doc.createTextNode(content);
			if (getController() instanceof Controller) {
				Controller ctrl = (Controller) getController();
				ctrl.setSkipEvents(true);
				// Remove all old values.
				NodeList nl = getNode().getChildNodes();
				for( int i = 0; i < nl.getLength(); ++i ) {
					getNode().removeChild(nl.item(i));
				}
				
				getNode().appendChild(textNode);
				ctrl.setSkipEvents(false);				
			}
		}
	}

	public Object getPresentation() {
		return null;
	}

}

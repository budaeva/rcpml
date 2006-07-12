package org.rcpml.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.rcpml.core.internal.Controller;
import org.rcpml.core.internal.CorePlugin;
import org.rcpml.core.internal.contentprovider.ContentProviderManager;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
		ContentProviderManager cpm = new ContentProviderManager();
		InputStream is = cpm.getStream(uri);
		if (is == null) {
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.IOEXCEPTION_ERROR, "unknown io exception", null);
			throw new CoreException(status);
		}
		try {
			return XML.loadDocument(new InputStreamReader(is), uri);
		} catch (IOException ioex) {
			ioex.printStackTrace();
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.IOEXCEPTION_ERROR, "io exception", ioex);
			throw new CoreException(status);
		} catch (SAXException saxe) {
			saxe.printStackTrace();
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.SAXEXCEPTION_ERROR, "sax exception", saxe);
			throw new CoreException(status);
		}
	}
}

package org.rcpml.core.internal;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.rcpml.core.internal.contentprovider.ContentProviderManager;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class RCPMLImpl {
	public static Document getDocument(String uri) throws CoreException {
		ContentProviderManager cpm = new ContentProviderManager();
		InputStream is = cpm.getStream(uri);
		if (is == null) {
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.IOEXCEPTION_ERROR, "unknown io exception", null);
			throw new CoreException(status);
		}
		try {
			Document doc = XML.loadDocument(is, uri);
			if (doc == null) {
				IStatus status = new Status(IStatus.ERROR,
						CorePlugin.PLUGIN_ID,
						CorePlugin.DOCUMENT_LOAD_EXCEPTION,
						"Document load exception", null);
				throw new CoreException(status);
			}
			return doc;
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

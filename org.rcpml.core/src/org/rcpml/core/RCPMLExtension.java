package org.rcpml.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.rcpml.core.internal.CorePlugin;
import org.rcpml.core.internal.contentprovider.ContentProviderManager;
import org.rcpml.core.internal.contentprovider.SelectedBundleContentProvider;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * RCPML Extension
 * 
 * @author andrey
 */
public class RCPMLExtension implements IExecutableExtension,
		IExecutableExtensionFactory {

	private IConfigurationElement config;

	private String propertyName;

	private String script;

	private Document loadDocument() throws CoreException {
		String bundleId = config.getDeclaringExtension()
				.getNamespaceIdentifier();
		Bundle bundle = Platform.getBundle(bundleId);
		ContentProviderManager cpm = new ContentProviderManager();
		cpm.setDefaultProvider(new SelectedBundleContentProvider(bundle));
		try {
			InputStream is = cpm.getStream(script);
			if (is == null) {
				IStatus status = new Status(IStatus.ERROR,
						CorePlugin.PLUGIN_ID, CorePlugin.IOEXCEPTION_ERROR,
						"unknown io exception", null);
				throw new CoreException(status);
			}
			return XML.loadDocument(is, script);

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

	public Object create() throws CoreException {
		Document doc = loadDocument();
		Object extension = RCPML.renderDocument(doc);
		if (extension instanceof IExecutableExtension) {
			IExecutableExtension ee = (IExecutableExtension) extension;
			ee.setInitializationData(config, propertyName, script);
		}
		return extension;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		this.config = config;
		this.propertyName = propertyName;
		if (data instanceof String) {
			script = (String) data;
		} else if (data instanceof Map) {
			script = (String) ((Map) data).get("script");
		}
	}
}

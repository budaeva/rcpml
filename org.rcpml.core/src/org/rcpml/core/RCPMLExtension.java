package org.rcpml.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.rcpml.core.xml.XML;
import org.rcpml.internal.core.CorePlugin;
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
		String bundleId = config.getDeclaringExtension().getNamespace();
		Bundle bundle = Platform.getBundle(bundleId);
		URL url = bundle.getEntry(script);
		if (url == null) {
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.SCRIPT_NOT_FOUND, MessageFormat.format(
							"Script {0} not found in plug-in {1}.",
							new Object[] {script, bundleId}), null);
			throw new CoreException(status);
		}
		try {
			url = Platform.resolve(url);
			InputStream is = url.openConnection().getInputStream();
			return XML.loadDocument(new InputStreamReader(is), script);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.IOEXCEPTION_ERROR, "io exception", ioe);
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
		String bundleId = config.getDeclaringExtension().getNamespace();
		Bundle bundle = Platform.getBundle(bundleId);
		Object extension = RCPML.renderDocument(doc, bundle);
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
		if( data instanceof String ) {
			script = (String) data;
		}
		else if( data instanceof Map ) {
			script = (String)((Map)data).get("script");
		}
	}
}

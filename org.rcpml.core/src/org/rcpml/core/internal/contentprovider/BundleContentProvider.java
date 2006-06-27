package org.rcpml.core.internal.contentprovider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.rcpml.core.contentprovider.IContentProvider;
import org.rcpml.core.internal.CorePlugin;

public class BundleContentProvider implements IContentProvider {
	public BundleContentProvider() {
	}

	public InputStream getStream(URI uri) throws CoreException {
		String bundleName = uri.getHost();

		Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.IOEXCEPTION_ERROR, "Bundle not found", null);
			throw new CoreException(status);
		}
		String script = uri.getQuery();
		URL url = bundle.getEntry(script);
		if (url == null) {
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.SCRIPT_NOT_FOUND, MessageFormat.format(
							"Script {0} not found in plug-in {1}.",
							new Object[] { script, bundleName }), null);
			throw new CoreException(status);
		}
		try {
			url = FileLocator.resolve(url);
			InputStream is = url.openConnection().getInputStream();
			return is;
		} catch (IOException ex) {
			ex.printStackTrace();
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.IOEXCEPTION_ERROR, "io exception", ex);
			throw new CoreException(status);
		}
	}
}

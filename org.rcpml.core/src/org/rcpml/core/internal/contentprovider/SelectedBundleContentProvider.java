package org.rcpml.core.internal.contentprovider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.rcpml.core.contentprovider.IContentProvider;
import org.rcpml.core.internal.CorePlugin;

public class SelectedBundleContentProvider implements IContentProvider {
	private Bundle fBundle;

	public SelectedBundleContentProvider(Bundle bundle) {
		this.fBundle = bundle;
	}

	public InputStream getStream(URI uri) throws CoreException {
		String script = uri.getPath();
		URL url = fBundle.getEntry(script);
		if (url == null) {
			IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
					CorePlugin.SCRIPT_NOT_FOUND, MessageFormat
							.format("Script {0} not found in plug-in {1}.",
									new Object[] { script,
											this.fBundle.getBundleId() }), null);
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

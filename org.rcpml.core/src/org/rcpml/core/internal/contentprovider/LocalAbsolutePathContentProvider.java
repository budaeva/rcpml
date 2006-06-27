package org.rcpml.core.internal.contentprovider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.rcpml.core.contentprovider.IContentProvider;
import org.rcpml.core.internal.CorePlugin;

public class LocalAbsolutePathContentProvider implements IContentProvider {
	public InputStream getStream(URI uri) throws CoreException {
		try {
			FileInputStream fis = new FileInputStream(uri.getPath());
			return fis;
		} catch (IOException ex) {
			throw new CoreException(new Status(Status.ERROR,
					CorePlugin.PLUGIN_ID, CorePlugin.IOEXCEPTION_ERROR,
					"Error to obtain stream", ex));
		}
	}
}

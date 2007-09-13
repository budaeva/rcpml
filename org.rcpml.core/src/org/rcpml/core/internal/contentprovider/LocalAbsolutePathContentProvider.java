package org.rcpml.core.internal.contentprovider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
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
		}
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if( workspace != null ) {
			IResource file= workspace.getRoot().findMember(uri.toString());
			if( file.exists() && file instanceof IFile) {
				return ((IFile)file).getContents();
			}
		}
		throw new CoreException(new Status(Status.ERROR,
				CorePlugin.PLUGIN_ID, CorePlugin.IOEXCEPTION_ERROR,
				"Error to obtain stream", null));
	}
}

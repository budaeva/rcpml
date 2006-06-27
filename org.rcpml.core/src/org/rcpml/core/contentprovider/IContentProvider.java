package org.rcpml.core.contentprovider;

import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.runtime.CoreException;

public interface IContentProvider {
	InputStream getStream( URI uri ) throws CoreException;
}

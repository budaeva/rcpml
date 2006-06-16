package org.rcpml.core.contentprovider;

import java.io.InputStream;

public interface IContentProvider {
	InputStream getStream( String uri );
}

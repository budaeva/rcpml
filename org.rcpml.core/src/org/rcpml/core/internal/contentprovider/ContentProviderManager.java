package org.rcpml.core.internal.contentprovider;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.rcpml.core.contentprovider.IContentProvider;

public class ContentProviderManager {
	private static final String CONTENTPROVIDER_EXT_POINT = "org.rcpml.core.contentProvider";

	private static final String SCHEMA_ATTR = "schema";

	private static final String CLASS_ATTR = "class";

	private Map sProviders = new HashMap();

	private IContentProvider fDefaultProvider;

	public ContentProviderManager() {
		initializeExtensions();
	}

	private void initializeExtensions() {
		IConfigurationElement[] confs = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(CONTENTPROVIDER_EXT_POINT);
		for (int ci = 0; ci < confs.length; ++ci) {
			IConfigurationElement configElement = confs[ci];
			String schema = configElement.getAttribute(SCHEMA_ATTR);
			try {
				Object cp = configElement.createExecutableExtension(CLASS_ATTR);
				if( cp != null && cp instanceof IContentProvider ) {
					this.sProviders.put( schema, cp );
				}
			} catch (CoreException ex) {
				ex.printStackTrace();
			}
		}
	}

	private IContentProvider getProvider(String path) {
		try {
			URI uri = new URI(path);
			String schema = uri.getScheme();
			if( schema != null ) {
				if( this.sProviders.containsKey(schema)) {
					return (IContentProvider)this.sProviders.get(schema);
				}
			}
			return getDefaultProvider();					
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private IContentProvider getDefaultProvider() {
		if (fDefaultProvider == null) {
			fDefaultProvider = new LocalAbsolutePathContentProvider();
		}
		return fDefaultProvider;
	}

	public void setDefaultProvider(IContentProvider provider) {
		this.fDefaultProvider = provider;
	}
	
	public InputStream getStream( String path ) throws CoreException {
		IContentProvider provider = this.getProvider(path);
		if( provider != null ) {
			try {
				return provider.getStream( new URI( path ));
			}
			catch( URISyntaxException ex ) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}

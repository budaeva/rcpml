package org.rcpml.core.internal.contentprovider;

import java.util.HashMap;
import java.util.Map;

import org.rcpml.core.contentprovider.IContentProvider;

public class ContentProviderManager {	
	private static Map sProviders = new HashMap();
	public static IContentProvider getProvider( String path ) {
		if( path.contains(":")) {
			
		}
		return null;
	}
}

package org.rcpml.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class Utils {

	public static InputStream openScript(String pathToSript, Bundle bundle) throws IOException {		
		URL url = bundle.getEntry(pathToSript);
		url = Platform.resolve(url);
		return url.openConnection().getInputStream();
	}

}

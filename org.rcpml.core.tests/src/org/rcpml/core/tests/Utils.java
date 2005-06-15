package org.rcpml.core.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class Utils {

	static InputStream openScript(String pathToSript) throws IOException {
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		URL url = bundle.getEntry(pathToSript);
		url = Platform.resolve(url);
		return url.openConnection().getInputStream();
	}

}

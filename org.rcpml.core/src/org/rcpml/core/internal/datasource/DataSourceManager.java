package org.rcpml.core.internal.datasource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.datasource.IDataSourceFactory;
import org.w3c.dom.Node;

public class DataSourceManager {
	private static final String CLASS_ATTR = "class";

	private static final String NAME_ATTR = "name";

	private static final String DATASOURCE_EXT_POINT = "org.rcpml.core.dataSource";

	private static DataSourceManager sDataSourceManager;

	private static Map sDataSourceFactorys = new HashMap();

	public static DataSourceManager getInstance() {
		if (sDataSourceManager == null) {
			sDataSourceManager = new DataSourceManager();
		}
		return sDataSourceManager;
	}

	public IDataSource getDataSource(String src, Node node ) {

		URI uri = null;
		try {
			uri = new URI(src);
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
			return null;
		}

		String name = uri.getHost();

		if (sDataSourceFactorys.containsKey(name)) {
			IDataSourceFactory factory = (IDataSourceFactory) sDataSourceFactorys
					.get(name);
			return factory.createDataSource(uri, node);
		}

		if (name == null) {
			System.err.println("DataSource Manager: incorrect datasource...");
			return null;
		}

		IConfigurationElement configs[] = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(DATASOURCE_EXT_POINT);
		if (configs != null) {
			for (int i = 0; i < configs.length; ++i) {
				IConfigurationElement configElement = configs[i];
				String dsName = configElement.getAttribute(NAME_ATTR);
				if (name.equals(dsName)) {
					try {
						IDataSourceFactory factory = (IDataSourceFactory) configElement
								.createExecutableExtension(CLASS_ATTR);
						return factory.createDataSource(uri, node );
					} catch (CoreException ex) {
						ex.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}

	public IDataSource getLocalDataSource() {
		// TODO: Add local xml xpath.
		return null;
	}
}

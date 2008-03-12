package org.rcpml.ui.dynamic;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

/**
 * @author Yuri Strot
 *
 */
public class DynamicRCPMLExtension implements IExecutableExtension,
		IExecutableExtensionFactory {

	private IConfigurationElement config;

	private String propertyName;

	private String script;

	public Object create() throws CoreException {
		Object extension = TemporaryExtension.getExtension();
		if (extension instanceof IExecutableExtension) {
			IExecutableExtension ee = (IExecutableExtension) extension;
			ee.setInitializationData(config, propertyName, script);
		}
		return extension;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		this.config = config;
		this.propertyName = propertyName;
	}
	
}

package org.rcpml.ui.dynamic;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DynamicRCPMLPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.rcpml.ui.dynamic";

	// The shared instance
	private static DynamicRCPMLPlugin plugin;
	
	/**
	 * The constructor
	 */
	public DynamicRCPMLPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	public static void log(Throwable t) {
		String message = t.getMessage();
		if (message == null)
			message = "";
		plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID,0, message, t));
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DynamicRCPMLPlugin getDefault() {
		return plugin;
	}

}

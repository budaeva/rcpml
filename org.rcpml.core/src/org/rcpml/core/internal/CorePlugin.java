package org.rcpml.core.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class CorePlugin extends Plugin {

	public final static int IOEXCEPTION_ERROR = 100;
	public final static int SAXEXCEPTION_ERROR = 101;

	public final static int SCRIPT_NOT_FOUND = 200;
	public final static int RENDERER_NOT_FOUND = 201;
	public static final int DOCUMENT_LOAD_EXCEPTION = 202;
	
	public final static String PLUGIN_ID = "org.rcpml.core";
	
	
	//The shared instance.
	private static CorePlugin plugin;
	
	/**
	 * The constructor.
	 */
	public CorePlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static CorePlugin getDefault() {
		return plugin;
	}

}

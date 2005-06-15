package org.rcpml.forms.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class FormsPlugin extends AbstractUIPlugin {
	
	private static FormsPlugin plugin;

	public FormsPlugin() {
		plugin = this;
	}
	
	public static FormsPlugin getDefault() {
		return plugin;
	}
}

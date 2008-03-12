package org.rcpml.ui.dynamic;

/**
 * @author Yuri Strot
 *
 */
public class TemporaryExtension {
	
	private static Object extension;
	
	static void setExtension(Object extension) {
		TemporaryExtension.extension = extension;
	}
	
	static Object getExtension() {
		return extension;
	}

}

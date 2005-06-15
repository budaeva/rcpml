package org.rcpml.core.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Platform;
import org.rcpml.core.IRenderer;

public class RenderManager {
	
	private final static String RENDERER_EXT_POINT = "org.rcpml.core.renderer";
	private final static String XMLNS_ATTR = "namespaceURI";
	private final static String CLASS_ATTR = "class";

	private static Map renderers;
	
	private static void loadRenderers() {
		renderers = new HashMap();
		IConfigurationElement[] conf = Platform.getExtensionRegistry().getConfigurationElementsFor(RENDERER_EXT_POINT);
		for(int i=0;i<conf.length;i++) {
			String ns = conf[i].getAttribute(XMLNS_ATTR);
			renderers.put(ns, conf[i]);
		}		
	}
	
	static IRenderer getRenderer(String xmlns) {
		if(renderers == null)
			loadRenderers();
		Object r = renderers.get(xmlns);
		if(r == null) return null;
		if(!(r instanceof IRenderer)) {
			try {
				r = ((IConfigurationElement)r).createExecutableExtension(CLASS_ATTR);
			} catch(CoreException e) {
				//TODO report error
				e.printStackTrace();
				return null;
			}
			renderers.put(xmlns, r);
		}
		return (IRenderer) r; 
	}
}

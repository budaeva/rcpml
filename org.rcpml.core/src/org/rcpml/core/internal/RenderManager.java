package org.rcpml.core.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.rcpml.core.IRendererFactory;

public class RenderManager {
	
	private final static String RENDERER_EXT_POINT = "org.rcpml.core.renderer";
	private final static String XMLNS_ATTR = "namespaceURI";
	private final static String CLASS_ATTR = "class";

	private static Map rendererFactories;
	
	private static void loadRenderers() {
		rendererFactories = new HashMap();
		IConfigurationElement[] conf = Platform.getExtensionRegistry().getConfigurationElementsFor(RENDERER_EXT_POINT);
		for(int i=0;i<conf.length;i++) {
			String ns = conf[i].getAttribute(XMLNS_ATTR);
			rendererFactories.put(ns, conf[i]);
		}		
	}
	
	static IRendererFactory getRendererFactory(String xmlns) {
		if(rendererFactories == null)
			loadRenderers();
		Object r = rendererFactories.get(xmlns);
		if(r == null) return null;
		if(!(r instanceof IRendererFactory)) {
			try {
				r = ((IConfigurationElement)r).createExecutableExtension(CLASS_ATTR);
			} catch(CoreException e) {
				//TODO report error
				e.printStackTrace();
				return null;
			}
			rendererFactories.put(xmlns, r);
		}
		return (IRendererFactory) r; 
	}
}

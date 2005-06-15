package org.rcpml.core.internal;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.rcpml.core.IRenderer;
import org.rcpml.core.IRendererFactory;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Node;

/**
 * Dispatches rendering according to node namespace URI.
 * 
 * @author andrey
 *
 */
public class DispatchingRenderer implements IRenderer {
	
	private Map renderers = new HashMap();
	
	private IRenderer getRenderer(Node node) {
		String xmlns = node.getNamespaceURI();
		IRenderer renderer = (IRenderer) renderers.get(xmlns);
		if(renderer == null) {
			IRendererFactory factory = RenderManager.getRendererFactory(xmlns);
			if (factory == null) {
				// IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
				// CorePlugin.RENDERER_NOT_FOUND, MessageFormat.format(
				// "Renderer not found for namespace {0}.",
				// new Object[] { xmlns }), null);
				// throw new CoreException(status);
				throw new RCPMLException(MessageFormat.format(
						"Renderer not found for namespace {0}.",
						new Object[] { xmlns }));
			}
			renderer = factory.createRenderer(this);
			renderers.put(xmlns, renderer);
		}
		return renderer;
	}

	public Object renderNode(Node node, Object target) {
		switch(node.getNodeType()) {
		case Node.DOCUMENT_NODE:
			return renderNode(node.getFirstChild(), target);
		case Node.TEXT_NODE:
			if(node.getNodeValue().trim().length() == 0) 
				return null;
			// use parent node renderer
			return getRenderer(node.getParentNode()).renderNode(node, target);
		}
		
		return getRenderer(node).renderNode(node, target);
	}

}

package org.rcpml.core.internal;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.rcpml.core.IController;
import org.rcpml.core.IRenderer;
import org.rcpml.core.IRendererFactory;
import org.rcpml.core.IScriptingContext;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Node;

/**
 * Dispatches rendering according to node namespace URI.
 * 
 * @author Andrey Platov
 */
public class Controller implements IController {
	
	private final static String CORE_RENDERER_URI = "http://rcpml.org/core";
	
	private Map renderers = new HashMap();
	private Bundle provider;
	
	public Controller(Bundle provider) {
		this.provider = provider;
	}
	
	Bundle getBundle() {
		return provider;
	}
	
	private IRenderer getRenderer(String xmlns) {
		IRenderer renderer = (IRenderer) renderers.get(xmlns);
		if(renderer == null) {
			IRendererFactory factory = RenderManager.getRendererFactory(xmlns);
			if (factory == null) {
				throw new RCPMLException(MessageFormat.format(
						"Renderer not found for namespace {0}.",
						new Object[] { xmlns }));
			}
			renderer = factory.createRenderer(this);
			renderers.put(xmlns, renderer);
		}
		return renderer;
	}
	
	private IScriptingContext getScriptingContext() {
		return (IScriptingContext) getRenderer(CORE_RENDERER_URI);
	}

	public Object renderNode(Node node, Object target) {
		switch(node.getNodeType()) {
		case Node.DOCUMENT_NODE:
			return renderNode(node.getFirstChild(), target);
		case Node.TEXT_NODE:
			if(node.getNodeValue().trim().length() == 0) 
				return null;
			// use parent node renderer
			Node parent = node.getParentNode();
			return getRenderer(parent.getNamespaceURI()).renderNode(node, target);
		case Node.COMMENT_NODE:
			return null;
		}
		
		return getRenderer(node.getNamespaceURI()).renderNode(node, target);
	}
	
	public Object executeScript(String script) {
		return getScriptingContext().executeScript(script);
	}

	public String getLanguageName() {
		return getScriptingContext().getLanguageName();
	}

}

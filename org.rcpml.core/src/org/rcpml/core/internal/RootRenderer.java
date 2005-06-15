package org.rcpml.core.internal;

import java.text.MessageFormat;

import org.rcpml.core.IRenderer;
import org.rcpml.core.ScriptError;
import org.w3c.dom.Node;

public class RootRenderer implements IRenderer {

	public Object renderNode(Node node, Object parent) {
		String xmlns = node.getNamespaceURI();
		if(xmlns == null)
			return null;
		IRenderer renderer = RenderManager.getRenderer(xmlns);
		if (renderer == null) {
			// IStatus status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID,
			// CorePlugin.RENDERER_NOT_FOUND, MessageFormat.format(
			// "Renderer not found for namespace {0}.",
			// new Object[] { xmlns }), null);
			// throw new CoreException(status);
			throw new ScriptError(MessageFormat.format(
					"Renderer not found for namespace {0}.",
					new Object[] { xmlns }));
		}
		return renderer.renderNode(node, this);
	}
}

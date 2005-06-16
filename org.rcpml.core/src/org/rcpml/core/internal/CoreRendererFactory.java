package org.rcpml.core.internal;

import org.rcpml.core.IController;
import org.rcpml.core.IRenderer;
import org.rcpml.core.IRendererFactory;

public class CoreRendererFactory implements IRendererFactory {

	public IRenderer createRenderer(IController controller) {
		return new CoreRenderer(controller);
	}


}

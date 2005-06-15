package org.rcpml.ui.internal;

import org.rcpml.core.IRenderer;
import org.rcpml.core.IRendererFactory;

public class UIRendererFactory implements IRendererFactory {

	public IRenderer createRenderer(IRenderer parent) {
		return new UIRenderer(parent);
	}

}

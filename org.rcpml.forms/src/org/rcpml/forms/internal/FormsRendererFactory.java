package org.rcpml.forms.internal;

import org.rcpml.core.IRenderer;
import org.rcpml.core.IRendererFactory;

public class FormsRendererFactory implements IRendererFactory {

	public IRenderer createRenderer(IRenderer parent) {
		return new FormsRenderer(parent);
	}

}

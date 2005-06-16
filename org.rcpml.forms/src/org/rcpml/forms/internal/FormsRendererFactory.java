package org.rcpml.forms.internal;

import org.rcpml.core.IController;
import org.rcpml.core.IRenderer;
import org.rcpml.core.IRendererFactory;

public class FormsRendererFactory implements IRendererFactory {

	public IRenderer createRenderer(IController parent) {
		return new FormsRenderer(parent);
	}

}

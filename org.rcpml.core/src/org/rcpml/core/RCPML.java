package org.rcpml.core;

import org.rcpml.core.internal.DispatchingRenderer;

public class RCPML {	
	
	public static IRenderer createRenderer() {
		return new DispatchingRenderer();
	}
	
}

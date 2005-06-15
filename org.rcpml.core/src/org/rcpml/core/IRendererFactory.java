package org.rcpml.core;

public interface IRendererFactory {

	IRenderer createRenderer(IRenderer parent);
}

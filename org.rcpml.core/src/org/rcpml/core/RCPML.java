package org.rcpml.core;

import org.eclipse.core.runtime.CoreException;
import org.rcpml.core.internal.RootRenderer;
import org.w3c.dom.Node;

public class RCPML {

	public static Object renderNode(Node node) throws CoreException {
		return new RootRenderer().renderNode(node, null);
	}
}

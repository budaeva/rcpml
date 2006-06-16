package org.rcpml.core.tests.extension.tests;

import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

public class TestContainerBridge extends AbstractBridge implements IBridge {
	private TestContainer fContainer;
	protected TestContainerBridge(Node node, IController controller) {
		super(node, controller, true);
		this.fContainer = new TestContainer( controller );
		System.out.println("Testcontainer created");
	}

	public Object getPresentation() {
		return fContainer;
	}

	public void dispose() {
		System.out.println("TestContainer:dispose");
	}

	public void update() {
		System.out.println("TestContainer:update");
	}

}

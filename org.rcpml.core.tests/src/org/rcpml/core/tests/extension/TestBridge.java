package org.rcpml.core.tests.extension;

import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.w3c.dom.Node;

public class TestBridge extends AbstractBridge {

	private String fName;

	public TestBridge(String name, Node node, IController container ) {
		super( node, container );
		this.fName = name;
		System.out.println("Create TestBridge:" + name);
	}

	public boolean canHasChildren() {
		return true;
	}

	public Object getName() {
		return this.fName;
	}

	public boolean supportSubChildren() {	
		return true;
	}

	public Object getPresentation() {		
		return null;
	}

}

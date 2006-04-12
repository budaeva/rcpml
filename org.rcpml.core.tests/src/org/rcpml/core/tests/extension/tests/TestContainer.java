package org.rcpml.core.tests.extension.tests;

import org.rcpml.core.IController;

public class TestContainer {
	private IController fController;
	public TestContainer(IController controller) {
		this.fController = controller;	
	} 
	public IController getController() {
		return this.fController;
	}
}

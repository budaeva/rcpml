package org.rcpml.core.bridge;

import java.util.Map;

import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;

public abstract class AbstractBridgeFactory implements IBridgeFactory {

	private IController fController;

	protected IController getController() {
		
		return this.fController;
	}

	public void setController(IController controller) {
		
		if (this.fController == null) {
			this.fController = controller;
		} else {
			throw new RCPMLException("Controller already set");
		}
	}	
}

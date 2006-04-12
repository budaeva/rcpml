package org.rcpml.core;

import java.util.Map;

import org.osgi.framework.Bundle;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.core.scripting.IScriptContextManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Hold information about bridges in document.
 * @author haiodo
 * Implements visitor witch can build bridges on visited nodes.
 */
public interface IController extends IVisitor {
	/**
	 * Return bridge associated for selected node. 
	 */
	IBridge getBridge( Node node );	
	
	/**
	 * Return associated plugin boundle.
	 */
	Bundle getBundle();
	
	/**
	 * Return document.	
	 */
	Document getDocument();
	
	/**
	 * Creates new bridge for selected node.
	 * Parent are getted from node parent. 
	 * So it should be created before. 
	 */
	IBridge createBridge(Node node);
	
	/**
	 * Return script context manager.
	 */
	IScriptContextManager getScriptManager();
	
	/**
	 * Return root bridge.
	 * Root bridge should hold information about bridge container.
	 * Also root bridge should implement comporent interface to return to.
	 * For example if this is used in RCPMLExtension and xml contains view
	 * Element then root bridge should implemen ViewPart interface.
	 */
	Object getPresentation();

	/**
	 * Called by bridges then bridge are need to be disposed.
	 * @param bridge
	 */
	void bridgeDisposed(IBridge bridge);
	
	void update();
}

package org.rcpml.core;

import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.scripting.IScriptContextManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Hold information about bridges in document.
 * 
 * @author haiodo Implements visitor witch can build bridges on visited nodes.
 */
public interface IController extends IVisitor {
	/**
	 * Return bridge associated for selected node.
	 */
	IBridge getBridge(Node node);

	/**
	 * Return document.
	 */
	Document getDocument();
	
	/**
	 * Return script context manager.
	 */
	IScriptContextManager getScriptManager();

	/**
	 * Return root bridge. Root bridge should hold information about bridge
	 * container. Also root bridge should implement comporent interface to
	 * return to. For example if this is used in RCPMLExtension and xml contains
	 * view Element then root bridge should implemen ViewPart interface.
	 */
	Object getPresentation();

	/**
	 * Called by bridges then bridge are need to be disposed.
	 * 
	 * @param bridge
	 */
	void bridgeDisposed(IBridge bridge);

	/**
	 * Update bridges.
	 *
	 */
	void update();
	
	IRCPMLConstructor getRootBridge();

	/**
	 * Return true, if root bundle implemetns IConstructor intterface.
	 * @return
	 */
	boolean isWithConstructor();
	
	/**
	 * Used to search first appropriatet datasource for element.
	 * 
	 * 1) Search on Bridges with DataSources.
	 * 2) Return first with specified name, or first founded.
	 */
	IDataSource getDataSource( Node node, String path );

	void requireFullUpdate();

}

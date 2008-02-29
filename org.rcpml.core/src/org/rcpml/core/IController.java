package org.rcpml.core;

import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.core.datasource.DataBinding;
import org.rcpml.core.datasource.IDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.xored.scripting.core.IScriptContextManager;

/**
 * Hold information about bridges in document.
 * 
 * @author haiodo Implements visitor witch can build bridges on visited nodes.
 */
public interface IController extends IVisitor {
	
	public static final String DOMATTR_MODIFIED = "DOMAttrModified";

	public static final String DOMNODE_INSERTED_INTO_DOCUMENT = "DOMNodeInsertedIntoDocument";

	public static final String DOMNODE_REMOVED_FROM_DOCUMENT = "DOMNodeRemovedFromDocument";

	public static final String DOMNODE_REMOVED = "DOMNodeRemoved";

	public static final String DOMNODE_INSERTED = "DOMNodeInserted";

	public static final String DOMSUBTREE_MODIFIED = "DOMSubtreeModified";
	
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
	
	void addDataSource(String name, IDataSource dataSource);
	
	/**
	 * Bind <code>DataBinding</code> with corresponding <code>IDataSource</code>
	 * 
	 * @param binding
	 */
	public void bind(DataBinding binding);

	void requireFullUpdate();

}

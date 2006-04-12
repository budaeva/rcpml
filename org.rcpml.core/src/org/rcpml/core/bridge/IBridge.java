package org.rcpml.core.bridge;

import org.rcpml.core.IController;
import org.w3c.dom.Node;


/**
 * A tagging interface that all bridges must implement. A bridge is
 * responsible on creating and maintaining an appropriate object
 * according to an Element.
 */
public interface IBridge {	
	
	/**
	 * Return associated controller.
	 */
	IController getController();
	
	/**
	 * Used to custom visit of sub nodes.
	 */
	void visit( IVisitor visitor );
		
	/**
	 * Return presentation object.
	 * Return null if no presentation are exist.
	 */
	Object getPresentation();
	
	/**
	 * Returns associated node
	 */
	Node getNode();
	
	/**
	 * Dispose.
	 * Then this method are called. Bridge should correctly dispose presentation.
	 * Disposed node removed from controller.
	 */	
	void dispose();
	
	/**
	 * Update should update presentation, from node.
	 */
	void update();
}

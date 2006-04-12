package org.rcpml.core.bridge;

import org.osgi.framework.Bundle;
import org.rcpml.core.IController;
import org.rcpml.core.dom.DOMUtils;
import org.w3c.dom.Node;

public abstract class AbstractBridge implements IBridge {
	private IController fController;
	private Node fNode;
	private boolean fVisitChildrens = true;

	protected AbstractBridge( Node node, IController controller, boolean visitChildrens ) {
		this.fController = controller;
		this.fNode = node;
		this.fVisitChildrens = visitChildrens;
	}
	protected AbstractBridge( Node node, IController controller ) {
		this.fController = controller;
		this.fNode = node;		
	}

	protected String getAttribute( String attrName ) {
		return DOMUtils.getAttribute(this.getNode(),attrName );
	}
	protected void setAttribute( String attrName, String value ) {
		DOMUtils.setAttribute(this.getNode(), attrName, value );
	}

	protected Bundle getBundle() {
		return this.fController.getBundle();
	}

	protected IBridge getParent() {
		Node parent = this.fNode.getParentNode();
		return this.fController.getBridge(parent);
	}

	public IController getController() {
		return this.fController;
	}

	protected void visitAllChildrens(IVisitor visitor) {
		Node current = this.getNode();
		for (Node child = current.getFirstChild(); child != null; child = child
				.getNextSibling()) {
			visitor.visit(child);
		}
	}

	public void visit(IVisitor visitor) {
		if( this.fVisitChildrens ) {
			this.visitAllChildrens( visitor );
		}
	}
	public Node getNode() {
		return this.fNode;
	}
	public void dispose() {		
	}
	public void update() {
	}
}

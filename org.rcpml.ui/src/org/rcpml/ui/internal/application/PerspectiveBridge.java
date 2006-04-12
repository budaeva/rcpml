package org.rcpml.ui.internal.application;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.w3c.dom.Node;

public class PerspectiveBridge extends AbstractBridge {
	private PerspectiveFactory fPerspectiveFactory;
	private List< String > fShownViews = new ArrayList< String >();
	
	protected PerspectiveBridge(Node node, IController controller) {
		super(node, controller, false );
		
		Node current = this.getNode();
		for (Node child = current.getFirstChild(); child != null; child = child.getNextSibling()) {
			if( child.getNodeType() == Node.ELEMENT_NODE ) {
				//Element e = (Element)child;
				String id = this.getAttribute( "id" );
				if( id != null && id.length() > 0 ) {
					fShownViews.add(id);
				}
			}
		}		
	}

	public Object getPresentation() {
		if( this.fPerspectiveFactory == null ) {
			this.fPerspectiveFactory = new PerspectiveFactory();
		}
		return this.fPerspectiveFactory;
	}
	void addView( String viewId ) {
		this.fShownViews.add( viewId );
	}
	private class PerspectiveFactory  implements IPerspectiveFactory {
		public void createInitialLayout(IPageLayout layout) {
			String editorArea = layout.getEditorArea();
			layout.setEditorAreaVisible(false);
			layout.setFixed(false);
			
			for( String id: fShownViews ) {
				layout.addStandaloneView( id, false, IPageLayout.LEFT, 0.25f, editorArea);
			}
		}		
	}
}

package org.rcpml.ui.internal.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IVisitor;
import org.rcpml.swt.ICompositeHolder;
import org.w3c.dom.Node;

public class EditPartTagFactory extends AbstractBridgeFactory {
	private static class BridgeEditPart extends EditorPart implements
			ICompositeHolder {
		private Composite fComposite;

		EditorBridge bridge;

		private boolean fInitialized = false;

		public BridgeEditPart(EditorBridge bridge) {
			super();
			this.bridge = bridge;
		}

		public void createPartControl(Composite parent) {
			fComposite = new Composite(parent, SWT.NONE);
			parent.setLayout(new FillLayout());
			this.fComposite.setLayout(new FillLayout());
			this.bridge.build();
			fInitialized = true;
			
			this.bridge.getController().getScriptManager().getDefaultContext();			
			this.bridge.executeInitScript( this );							
		}

		public boolean isInitialized() {
			return fInitialized;
		}

		public void setFocus() {
			this.fComposite.setFocus();
		}

		public Composite getComposite() {
			return this.fComposite;
		}

		public void dispose() {
			bridge.disposeBridge();
		}

		public void doSave(IProgressMonitor monitor) {
			// TODO Auto-generated method stub
			
		}

		public void doSaveAs() {
			// TODO Auto-generated method stub
			
		}

		public void init(IEditorSite site, IEditorInput input) throws PartInitException {
			setSite(site);
			setInput(input);							
			this.bridge.getController().getScriptManager().getDefaultContext().bindObject("editorInput", input );
		}

		public boolean isDirty() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isSaveAsAllowed() {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private static class EditorBridge extends AbstractBridge {		
		private BridgeEditPart fEditPart;

		protected EditorBridge(Node node, IController container) {
			super(node, container);
			fEditPart = new BridgeEditPart(this);
		}

		public void executeInitScript( EditorPart part ) {
			this.getController().getScriptManager().getDefaultContext().bindObject("editor", part );			
			executeScript("oninit");
		}

		private void executeScript( String attrName ) {
			String initScript = getAttribute( attrName );
			if( initScript.length() > 0 ) {
				this.getController().getScriptManager().executeScript( initScript );
			}
		}

		public void build() {
			this.visitAllChildrens(this.getController());
		}

		public Object getPresentation() {
			return this.fEditPart;
		}

		public void visit(IVisitor visitor) {
			if (this.fEditPart.isInitialized()) {
				this.visitAllChildrens(visitor);
			}
		}

		public void update() {
			Composite composite = this.fEditPart.getComposite();
			if (composite != null) {
				composite.layout();
			}
		}
		
		public void disposeBridge() {
			this.getController().bridgeDisposed(this);
		}
	}

	public EditPartTagFactory() {
		super();
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new EditorBridge(node, this.getController());
	}

}

package org.rcpml.ui.internal.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.rcpml.core.BridgeVisitor;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.ICompositeHolder;
import org.w3c.dom.Node;

import com.xored.scripting.core.ScriptException;

/**
 * @author Yuri Strot
 *
 */
public class BridgeMultiEditPart extends MultiPageEditorPart implements
		ICompositeHolder, IBridgeEditorPart {
	private Node node;
	private IController container;
	private AbstractBridge bridge;
	
	private String PAGE_NAME_ATTR = "pageName";

	private boolean fInitialized = false;
	private boolean dirty;
	private StateChanger stateChanger = new StateChanger();
	
	public BridgeMultiEditPart(Node node, 
			IController container, AbstractBridge bridge) {
		this.node = node;
		this.container = container;
		this.bridge = bridge;
	}
	
	private class StateChanger implements SelectionListener, ModifyListener {
		
		public void widgetDefaultSelected(SelectionEvent e) {
			update();
		}
		
		public void widgetSelected(SelectionEvent e) {
			update();
		}

		public void modifyText(ModifyEvent e) {
			update();
		}
		
		private void update() {
			firePropertyChange(PROP_DIRTY);
			dirty = true;
		}
		
	}
	
	private void addPages() {
		BridgeVisitor bvisitor = new BridgeVisitor(container, PAGE_NAME_ATTR) {
			
			private int index;

			protected void visit(IBridge bridge, String value) {
				Object presentation = bridge.getPresentation();
				if (presentation != null) {
					Composite composite = null;
					if (presentation instanceof Composite) {
						composite = (Composite) presentation;
					} else if (presentation instanceof ICompositeHolder) {
						composite = ((ICompositeHolder) presentation)
								.getComposite();
					}
					if (composite != null) {
						while(composite.getParent() != null) {
							if (composite.getParent() instanceof CTabFolder) {
								break;
							}
							composite = composite.getParent();
						}
						if (composite.getParent() == null)
							return;
						addPage(composite);
						if (value == null || value.length() == 0)
							value = index + "";
						setPageText(index, value);
						index++;
					}
				}
			}
		};
		bvisitor.visit(node);
		addListeners(getContainer());
	}
	
	private void addListeners(Composite parent) {
		Control[] children = parent.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Composite) {
				addListeners((Composite)children[i]);
			}
			else if (children[i] instanceof Button) {
				((Button)children[i]).addSelectionListener(stateChanger);
			}
			else if (children[i] instanceof Text) {
				((Text)children[i]).addModifyListener(stateChanger);
			}
		}
	}

	protected void createPages() {
		fInitialized = true;
		addPages();
		try {
			container.getScriptManager().getDefaultContext();
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}			
		executeInitScript();
	}

	public void executeInitScript() {
		try {
			container.getScriptManager().getDefaultContext().bindObject("editor", this );
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		executeScript("oninit");
	}

	private void executeScript( String attrName ) {
		String initScript = "";//getAttribute( attrName );
		if( initScript.length() > 0 ) {
			try {
				container.getScriptManager().executeScript( initScript );
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isInitialized() {
		return fInitialized;
	}

	public void setFocus() {
		getContainer().setFocus();
	}

	public Composite getComposite() {
		return getContainer();
	}

	public void dispose() {
		container.bridgeDisposed(bridge);
	}

	public void doSave(IProgressMonitor monitor) {
		dirty = false;
		firePropertyChange(PROP_DIRTY);
	}

	public void doSaveAs() {
		doSave(null);
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);							
		try {
			this.bridge.getController().getScriptManager().getDefaultContext().bindObject("editorInput", input );
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public boolean isSaveAsAllowed() {
		return true;
	}
}

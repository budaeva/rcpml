/**
 * 
 */
package org.rcpml.ui.internal.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.rcpml.swt.ICompositeHolder;

import com.xored.scripting.core.ScriptException;

class BridgeEditPart extends EditorPart implements ICompositeHolder,
		IBridgeEditorPart {
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

//		try {
			this.bridge.getController().getScriptManager().getDefaultContext();
//		} catch (ScriptException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.bridge.executeInitScript(this);
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

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
//		try {
			this.bridge.getController().getScriptManager().getDefaultContext()
					.bindObject("editorInput", input);
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
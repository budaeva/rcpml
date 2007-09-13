package org.rcpml.tools.ui.internal.browser;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class RCPMLBrowserEditor extends EditorPart {

	private RCPMLBrowser browser;

	public RCPMLBrowserEditor() {
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		if( input instanceof IFileEditorInput ) {
			if( browser != null ) {
				browser.renderFile(((IFileEditorInput)input).getFile());
			}
		}
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void createPartControl(Composite parent) {
		this.setTitleToolTip("Editor");
		browser = new RCPMLBrowser(parent, SWT.NONE);
		IEditorInput input = this.getEditorInput();
		if( input instanceof IFileEditorInput ) {
			if( browser != null ) {
				IFile file = ((IFileEditorInput)input).getFile();
				browser.renderFile(file);
			}
		}
		this.setPartName("RCPML Browser:" + input.getName() );
	}

	public void setFocus() {
		browser.setFocus();
	}

	public void navigate(String uri) {
		if( browser != null ) {
			browser.navigate(uri);
		}
	}
}

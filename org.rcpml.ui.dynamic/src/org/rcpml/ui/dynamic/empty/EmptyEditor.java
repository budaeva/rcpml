package org.rcpml.ui.dynamic.empty;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * @author Yuri Strot
 *
 */
public class EmptyEditor extends EditorPart {

	public void doSave(IProgressMonitor monitor) {
    }

	public void doSaveAs() {
    }

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    }

	public boolean isDirty() {
	    return false;
    }

	public boolean isSaveAsAllowed() {
	    return false;
    }

	public void createPartControl(Composite parent) {
    }

	public void setFocus() {
    }

}

package org.rcpml.ui.dynamic;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

/**
 * @author Yuri Strot
 *
 */
public class OpenRCPMLAction implements IActionDelegate {
	
	private IStructuredSelection selection;

	public void run(IAction action) {
		Object sel = selection.getFirstElement();
		if (sel instanceof IFile) {
			IFile file = (IFile) sel;
			DynamicRCPMLLoader.load(file.getLocation().toString(), true);
		}
    }

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection)selection;
		}
    }

}

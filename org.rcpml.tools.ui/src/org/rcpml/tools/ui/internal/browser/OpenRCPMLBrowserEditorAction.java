package org.rcpml.tools.ui.internal.browser;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class OpenRCPMLBrowserEditorAction implements IWorkbenchWindowActionDelegate {
	private String uri;
	public OpenRCPMLBrowserEditorAction() {
		
	}
	public OpenRCPMLBrowserEditorAction(String uri) {
		this.uri = uri;
	}
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			final String u = this.uri;
			IEditorInput dummyinput = new IEditorInput() {

				public boolean exists() {
					return true;
				}

				public ImageDescriptor getImageDescriptor() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getName() {
					if( u != null ) {
						return "" + u;
					}
					return "[none]";
				}

				public IPersistableElement getPersistable() {
					return null;
				}

				public String getToolTipText() {
					return "RCPML Editor";
				}

				public Object getAdapter(Class adapter) {
					return null;
				}
				
			};
			IEditorPart openEditor = IDE.openEditor(window.getActivePage(), dummyinput, "org.rcpml.tools.ui.browser");
			if( this.uri != null || openEditor instanceof RCPMLBrowserEditor ) {
				RCPMLBrowserEditor e = (RCPMLBrowserEditor) openEditor;
				e.navigate(uri);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}

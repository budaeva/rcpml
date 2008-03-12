package org.rcpml.ui.dynamic;

import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.rcpml.core.RCPML;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Yuri Strot
 *
 */
public class DynamicRCPMLLoader {
	
	private static final String VIEW_ID = "org.rcpml.ui.dynamic.view";
	private static final String EDITOR_ID = "org.rcpml.ui.dynamic.editor";
	
	public static void load(IFile file) {
		Object extension = open(file.getLocation().toString());
		TemporaryExtension.setExtension(extension);
		if (extension instanceof IViewPart) {
			try {
				IWorkbenchPage page = getPage();
				IViewPart part = page.findView(VIEW_ID);
				if (part != null) {
					page.hideView(part);
				}
				page.showView(VIEW_ID);
            }
            catch (PartInitException e) {
            	throwException(e);
            }
		}
		else if (extension instanceof IEditorPart) {
			try {
				IWorkbenchPage page = getPage();
				FileEditorInput input = new FileEditorInput(file);
				page.openEditor(input, EDITOR_ID);
            }
            catch (PartInitException e) {
            	throwException(e);
            }
		}
		else if (extension instanceof Dialog) {
			Dialog dialog = (Dialog)extension;
			dialog.open();
		}
		else if (extension instanceof Shell) {
			Shell shell = (Shell)extension;
			shell.open();
		}
		
	}
	
	private static IWorkbenchPage getPage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}
	
	protected static Object open(String path) {
        try {
        	Document doc = loadDocument(path);
			Object extension = RCPML.renderDocument(doc);
			return extension;
        }
        catch (IOException e) {
        	throwException(e);
        }
        catch (SAXException e) {
        	throwException(e);
        }
        return null;
	}
	private static void throwException(Exception e) {
		e.printStackTrace();
		DynamicRCPMLPlugin.log(e);
	}

	protected static Document loadDocument(String path) throws IOException, SAXException {
		FileInputStream stream = new FileInputStream(path);
		return XML.loadDocument(stream, null);
	}

}

package org.rcpml.ui.dynamic;

import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
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
	
	/**
	 * Load RCPML view (editor, dialog...) by source. The source parameter can be
	 * a RCPML file content or path to the RCPML file 
	 * 
	 * @param source RCPML file content or path to the file
	 * @param asPath true, when source used as a path to the file and false when
	 * source used as a RCPML file content
	 */
	public static void load(final String source, final boolean asPath) {
		Display.getDefault().asyncExec(new Runnable() {
		
			public void run() {
				Object extension = open(source, asPath);
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
						if (asPath) {
						    IPath filePath = new Path( source  );
						    IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation( filePath );
							FileEditorInput input = new FileEditorInput(file);
							page.openEditor(input, EDITOR_ID);
						}
						else {
							throwException(new IllegalArgumentException("editor input can't be found"));
						}
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
		
		});
	}
	
	private static IWorkbenchPage getPage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}
	
	protected static Object open(String source, boolean asPath) {
        try {
        	Document doc = asPath ? loadDocument(source) : loadDocumentFromString(source);
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
	
	protected static Document loadDocumentFromString(String source) throws IOException, SAXException {
		StringInputStream stream = new StringInputStream(source);
		return XML.loadDocument(stream, null);
	}

	protected static Document loadDocument(String path) throws IOException, SAXException {
		FileInputStream stream = new FileInputStream(path);
		return XML.loadDocument(stream, null);
	}

}

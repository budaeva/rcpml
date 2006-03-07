package org.rcpml.swt.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;

public class AddButtonTest extends TestCase
{	
	public void testBuildTree01() throws Exception {
		System.out.println("testBuildTree01");
				
		Document doc = XML.loadDocument( new InputStreamReader(Utils.openScript("/scripts/add_buttons_0.xml", TestsPlugin.getDefault().getBundle())), "/scripts/add_buttons_0.xml");
		
		Display display = new Display();
		
		Shell rootShell = null;
		
		Object shell = RCPML.renderDocument( doc, TestsPlugin.getDefault().getBundle() );
		if( shell instanceof Shell ){
			rootShell = (Shell)shell;			
			rootShell.pack();
			rootShell.open();
		}		
		
		if( rootShell != null ) {
			while (!rootShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
		display.dispose();
	}
}

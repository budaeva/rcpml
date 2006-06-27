package org.rcpml.forms.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;
import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;

public class FormsTest extends TestCase
{	
	public void testForms01() throws Exception {
		System.out.println("testBuildTree01");
				
		Document doc = XML.loadDocument( new InputStreamReader(Utils.openScript("/scripts/sample0.xml", TestsPlugin.getDefault().getBundle())), "/script/sample0.xml");
		
		Display display = new Display();
		
		Shell rootShell = new Shell();
		rootShell.setLayout(new FillLayout());
		
		Object shell = RCPML.renderDocument( doc );
		if( shell instanceof ViewPart ) {
			ViewPart vp = (ViewPart)shell;
			vp.createPartControl(rootShell);
		}
					
		rootShell.pack();
		rootShell.open();		
		
		if( rootShell != null ) {
			while (!rootShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
		display.dispose();
	}
}

package org.rcpml.core.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;

public class SvgTest extends TestCase
{	
	public void testSvg01() throws Exception {
		System.out.println("svgBuildTree01");
				
		Document doc = XML.loadDocument( new InputStreamReader(Utils.openScript("/scripts/test3.xml", TestsPlugin.getDefault().getBundle())), "/script/test3.xml");
		
		Display display = new Display();
		
		Shell rootShell = null;
		
		Object shell = RCPML.renderDocument( doc );
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

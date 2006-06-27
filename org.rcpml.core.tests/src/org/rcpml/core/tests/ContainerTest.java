package org.rcpml.core.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;

public class ContainerTest extends TestCase
{	
	public void testBuildTree01() throws Exception {
		System.out.println("testBuildTree01");
				
		Document doc = XML.loadDocument( new InputStreamReader(Utils.openScript("/scripts/test2.xml", TestsPlugin.getDefault().getBundle())), "/script/test2.xml");
		
		Display display = new Display();
		
		Shell rootShell = null;
		
		Object shell = RCPML.renderDocument( doc );
		if( shell instanceof Shell ){
			rootShell = (Shell)shell;			
			rootShell.pack();
			rootShell.open();
		}
		
		Shell s = new Shell( display );
		Text st =  new Text( s, SWT.WRAP | SWT.H_SCROLL );
		//st.setWordWrap(true);
		s.setLayout(new GridLayout());
		st.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
		st.setText("ajhglaksdjfhlaksjdhfklasjdhfklajsdhflkajsdhflaksjdhfaksdjhflkajdhfkajsdhflkajsdhflkajsdhflkajsdhf aksdjfhaksjdhflkajsdhflkajsdhfkfjahsdklfjh alsdkfjhalksdjfhlaksdjfakdjsfhla alskdjfhaksdjfhkasdjhfkajsdhf alsdkjfhalksdjfhalksdjfhalksdjf askdjfhaksdjfhaksjdhflaksjdfhlkadjfhlaksdjfh asdkfjhalsdkjfhaktyyqeejghalskgtyakjrhakj aksdyaiweb5rlakejhrak.sruoraiweu a osiruaevi5aeiuraoei5ub aoeiu5aoi5uba;owiu5 ajhglaksdjfhlaksjdhfklasjdhfklajsdhflkajsdhflaksjdhfaksdjhflkajdhfkajsdhflkajsdhflkajsdhflkajsdhf aksdjfhaksjdhflkajsdhflkajsdhfkfjahsdklfjh alsdkfjhalksdjfhlaksdjfakdjsfhla alskdjfhaksdjfhkasdjhfkajsdhf alsdkjfhalksdjfhalksdjfhalksdjf askdjfhaksdjfhaksjdhflaksjdfhlkadjfhlaksdjfh asdkfjhalsdkjfhaktyyqeejghalskgtyakjrhakj aksdyaiweb5rlakejhrak.sruoraiweu a osiruaevi5aeiuraoei5ub aoeiu5aoi5uba;owiu5");
		//st.setTextLimit(200);
		
		s.open();
		s.pack();
		
		if( rootShell != null ) {
			while (!rootShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
		display.dispose();
	}
}

package org.rcpml.core.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;

public class StylesTests extends TestCase {
	public void testStyles001() throws Exception {
		System.out.println("testBuildTree01");
		
		Document doc = XML.loadDocument( new InputStreamReader(Utils.openScript("/scripts/style0.xml", TestsPlugin.getDefault().getBundle())), "/scripts/style0.xml");
				
		Object shell = RCPML.renderDocument( doc );
		
	}
}

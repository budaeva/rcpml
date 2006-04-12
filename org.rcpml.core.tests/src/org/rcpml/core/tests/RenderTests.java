package org.rcpml.core.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;

public class RenderTests extends TestCase {

	public void test1() throws Exception {
		Document doc = XML.loadDocument(
				new InputStreamReader(Utils.openScript("/scripts/render.xml", TestsPlugin.getDefault().getBundle())), "/script/render.xml");
		RCPML.renderDocument(doc);
	}
}

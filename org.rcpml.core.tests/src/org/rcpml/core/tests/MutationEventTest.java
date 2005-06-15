package org.rcpml.core.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class MutationEventTest extends TestCase implements EventListener {
	
	private boolean test1Fired = false;

	public void test1() throws Exception {
		Document doc = XML.loadDocument(new InputStreamReader(Utils.openScript("/scripts/test1.xml")), "/script/test1.xml");
		EventTarget et = (EventTarget) doc;
		et.addEventListener("DOMAttrModified", this, true);
		Node form = doc.getFirstChild();
		form.getAttributes().getNamedItem("id").setNodeValue("1");
		assertEquals(true, test1Fired);
	}
	
	public void handleEvent(Event evt) {
		test1Fired = true;
	}

}

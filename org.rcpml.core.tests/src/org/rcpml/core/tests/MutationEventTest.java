package org.rcpml.core.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.rcpml.core.RCPML;
import org.rcpml.core.utils.Utils;
import org.rcpml.core.xml.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class MutationEventTest extends TestCase implements EventListener {
	
	private boolean test1Fired = false;

	public void test1() throws Exception {
		Document doc = XML.loadDocument(new InputStreamReader(Utils.openScript("/scripts/test1.xml", TestsPlugin.getDefault().getBundle())), "/script/test1.xml");
		EventTarget et = (EventTarget) doc;
		et.addEventListener("DOMAttrModified", this, true);
		Node form = doc.getFirstChild();
		form.getAttributes().getNamedItem("id").setNodeValue("1");
		assertEquals(true, test1Fired);
	}
	
	public void handleEvent(Event evt) {
		System.out.print("dom event:" + evt.getType() + ":");
		EventTarget target = evt.getTarget();
		if( target instanceof Element ) {
			Element e = (Element)target;
			String name = e.getLocalName();
			if( name == null ) {
				name = e.getNodeName();
			}
			System.out.println(name);
		}
		test1Fired = true;
	}
	
	public void test02() throws Exception {
		System.out.println("02");
		Document doc = XML.createDocument("http://rcpml.org/test.xml", "http://rcpml.org/ui", "view" );
		EventTarget et = (EventTarget) doc;
		et.addEventListener("DOMSubtreeModified", this, true);
		et.addEventListener("DOMNodeInserted", this, true);
		et.addEventListener("DOMNodeRemoved", this, true);
		et.addEventListener("DOMNodeRemovedFromDocument", this, true );
		et.addEventListener("DOMNodeInsertedIntoDocument", this, true );
		et.addEventListener("DOMAttrModified", this, true );
		
		Element form = doc.createElementNS( "http://rcpml.org/ui", "form" );
		Element view = doc.getDocumentElement();
		view.appendChild(form);
		System.out.println("cool");
	}
	public void test03() throws Exception {
		System.out.println("03");
		Document doc = XML.loadDocument(new InputStreamReader(Utils.openScript("/scripts/test_script_0.xml", TestsPlugin.getDefault().getBundle())), "/scripts/test_script_0.xml");
		Object presetation = RCPML.renderDocument( doc, TestsPlugin.getDefault().getBundle() );
		System.out.println("cool");
	}

}

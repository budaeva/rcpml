package org.rcpml.core.xml;

import java.io.IOException;
import java.io.Reader;

import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.rcpml.core.internal.dom.RCPDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XML {

	public static Document loadDocument(Reader reader, String documentURI)
			throws IOException, SAXException {

		DOMImplementation impl = RCPDOMImplementation.getDOMImplementation();
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXDocumentFactory f = new SAXDocumentFactory(impl, parser);
		Document doc = f.createDocument(documentURI, reader);
		return doc;
	}

	public static Document createDocument(String documentURI, String namespace,
			String qualifiedName) throws IOException, SAXException {
		
		DOMImplementation impl = RCPDOMImplementation.getDOMImplementation();
		Document document = impl.createDocument(namespace, qualifiedName, null);
		document.setDocumentURI(documentURI);
		return document;
	}
}

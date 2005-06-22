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
	
	public static Document loadDocument(Reader reader, String documentURI) throws IOException, SAXException {
		/*StandardParserConfiguration config = new StandardParserConfiguration();
		DOMParser builder = new DOMParser(config);
		builder.parse(new InputSource(reader));
		return builder.getDocument();*/
		DOMImplementation impl = RCPDOMImplementation.getDOMImplementation();
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXDocumentFactory f = new SAXDocumentFactory(impl, parser);
		return f.createDocument(documentURI, reader);
	}

}

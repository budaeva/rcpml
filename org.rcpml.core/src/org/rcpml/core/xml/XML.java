package org.rcpml.core.xml;

import java.io.IOException;
import java.io.Reader;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.StandardParserConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XML {
	
	public static Document loadDocument(Reader reader, String scriptName) throws IOException, SAXException {
		StandardParserConfiguration config = new StandardParserConfiguration();
		DOMParser builder = new DOMParser(config);
		builder.parse(new InputSource(reader));
		return builder.getDocument();
	}

}

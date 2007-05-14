package org.rcpml.core.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

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
		Document doc = f.createDocument(documentURI, convertReader( reader ));
		return doc;
	}

	private static Reader convertReader(Reader reader) {
		StringBuffer buffer = new StringBuffer();
		try {
			int c = reader.read();
			while( c != -1 ) {
				buffer.append((char)c);
				c = reader.read();
			}
			int pos = buffer.indexOf("<?xml");
			if( pos != -1 ) {
				int pos2 = buffer.indexOf("?>", pos);
				if( pos2 != -1 ) {
					String xmlHeader = buffer.substring(pos, pos2);
					if( xmlHeader != null ) {
						String pattern = "encoding=\"";
						int encPos = xmlHeader.indexOf(pattern);
						if( encPos != -1 ) {
							int pos3 = encPos + pattern.length();
							String encoding = xmlHeader.substring(pos3, xmlHeader.indexOf('\"', pos3));
							if( encoding != null ) {
								Charset charset = Charset.forName(encoding);
								if( charset != null ) {
									CharsetDecoder decoder = charset.newDecoder();
									CharBuffer cb = decoder.decode(ByteBuffer.wrap(buffer.toString().getBytes()));
									String newContent = cb.toString();
									return new StringReader( newContent );
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new StringReader(buffer.toString());
	}

	public static Document loadDocument(InputStream stream, String documentURI)
			throws IOException, SAXException {

		DOMImplementation impl = RCPDOMImplementation.getDOMImplementation();
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXDocumentFactory f = new SAXDocumentFactory(impl, parser);
		Document doc = f.createDocument(documentURI, stream);
		return doc;
	}

	public static Document createDocument(String documentURI, String namespace,
			String qualifiedName) throws IOException, SAXException {

		DOMImplementation impl = RCPDOMImplementation.getDOMImplementation();
		Document document = impl.createDocument(namespace, qualifiedName, null);
//		document.setDocumentURI(documentURI);
		return document;
	}
}

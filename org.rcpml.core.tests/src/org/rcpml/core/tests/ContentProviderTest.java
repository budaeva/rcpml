package org.rcpml.core.tests;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.rcpml.core.internal.contentprovider.ContentProviderManager;

public class ContentProviderTest extends TestCase {
	public void testCP001() throws URISyntaxException {		
		URI uri = new URI("/scripts/myscript.xml");
		assertNull(uri.getScheme());
		URI uri2 = new URI("bundle://org.rcpml.core.tests:87/scripts/myscript.xml#cool");
		assertNotNull(uri2);
		System.out.println("path:" +uri2.getPath());
		System.out.println("fragment:" +uri2.getFragment());
		System.out.println("query:" +uri2.getQuery());
		System.out.println("host:" +uri2.getHost());
		System.out.println("host:" +uri2.getPort());
		
		URI uri3 = new URI("mySource");
		System.out.println(uri3.getHost());
	}
	public void testCP002() throws Exception {
		ContentProviderManager cpm = new ContentProviderManager();
		InputStream is0 = cpm.getStream("bundle://org.rcpml.core.tests/scripts/test3.xml");
		assertNotNull("test3.xml must exist", is0);
		InputStream is1 = cpm.getStream("bundle://org.rcpml.example.forms/views/sample7.xml");
		assertNotNull("bundle://org.rcpml.example.forms/views/sample7.xml must exist", is1);
		
		InputStream is2 = cpm.getStream("file:///temp/file.xml");		
		assertNotNull("file:///temp/file.xml", is2);
	}	
}

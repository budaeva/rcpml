package org.rcpml.core.tests.datasource;

import junit.framework.TestCase;

import org.rcpml.core.datasource.DataSourceElementBinding;
import org.rcpml.core.datasource.DataSourceUtils;

public class DataSourceElementBindingTests extends TestCase {
	public void testBindings001() throws Exception {
		DataSourceElementBinding b1 = new DataSourceElementBinding("b1", String.class ); 
		DataSourceElementBinding b2 = new DataSourceElementBinding("b2", String.class );
		DataSourceElementBinding b3 = new DataSourceElementBinding("b3", String.class );
		DataSourceElementBinding b4 = new DataSourceElementBinding("b4", String.class );
		DataSourceElementBinding b5 = new DataSourceElementBinding("b5", String.class );
		DataSourceUtils.bindDataSourceElement(b1, b2);		
		System.out.println("b1:" + b1.getValue());
		assertEquals("b1", (String)b1.getValue());
		System.out.println("b2:" + b2.getValue());
		assertEquals("b1", (String)b2.getValue());
		System.out.println("b3:" + b3.getValue());
		System.out.println("b4:" + b4.getValue());
		System.out.println("b5:" + b5.getValue());
		DataSourceUtils.bindDataSourceElement(b2, b3);
		assertEquals("b1", (String)b3.getValue());
		System.out.println("b1:" + b1.getValue());
		System.out.println("b2:" + b2.getValue());
		System.out.println("b3:" + b3.getValue());
		System.out.println("b4:" + b4.getValue());
		System.out.println("b5:" + b5.getValue());
		b3.setValue("b3`");
		assertEquals("b3`", (String)b1.getValue());
		assertEquals("b3`", (String)b2.getValue());
		assertEquals("b3`", (String)b1.getValue());
		System.out.println("b1:" + b1.getValue());
		System.out.println("b2:" + b2.getValue());
		System.out.println("b3:" + b3.getValue());
		System.out.println("b4:" + b4.getValue());
		System.out.println("b5:" + b5.getValue());
		DataSourceUtils.bindDataSourceElement(b3, b4);
		DataSourceUtils.bindDataSourceElement(b5, b1);			
		System.out.println("b1:" + b1.getValue());
		System.out.println("b2:" + b2.getValue());
		System.out.println("b3:" + b3.getValue());
		System.out.println("b4:" + b4.getValue());
		System.out.println("b5:" + b5.getValue());
	}
}

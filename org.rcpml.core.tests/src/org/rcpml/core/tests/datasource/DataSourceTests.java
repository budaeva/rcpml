package org.rcpml.core.tests.datasource;

import org.rcpml.core.RCPML;

import junit.framework.TestCase;

public class DataSourceTests extends TestCase {
	
	public void testDataSource001() throws Exception {
		Object o = RCPML.renderURI("bundle://org.rcpml.core.tests/scripts/dataSource0.xml");
		assertNotNull(o);
	}
}

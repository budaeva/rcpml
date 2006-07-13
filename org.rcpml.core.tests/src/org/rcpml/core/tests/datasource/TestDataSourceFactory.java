package org.rcpml.core.tests.datasource;

import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.datasource.IDataSourceElement;
import org.rcpml.core.datasource.IDataSourceFactory;
import org.w3c.dom.Node;

public class TestDataSourceFactory implements IDataSourceFactory {
	public static class TestDataSource implements IDataSource {		
		public TestDataSource( ) {			
		}
		public void bind(IDataSourceElement object, String path) {		
		}
		public String getName() {
			return "testDataSource";
		}		
	}
	public IDataSource createDataSource(Node node) {		
		return new TestDataSource(); 
	}	
}

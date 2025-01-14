package org.rcpml.core.tests.datasource;

import org.rcpml.core.IController;
import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.datasource.IDataSourceElementBinding;
import org.rcpml.core.datasource.IDataSourceFactory;
import org.w3c.dom.Node;

public class TestDataSourceFactory implements IDataSourceFactory {
	public static class TestDataSource implements IDataSource {		
		public TestDataSource( ) {			
		}
		public void bind(IDataSourceElementBinding object, String path) {		
		}
		public String getName() {
			return "testDataSource";
		}		
	}
	
	public IDataSource createDataSource(IController controller, Node node) {
		return new TestDataSource(); 
	}
}

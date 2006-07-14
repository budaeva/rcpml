package org.rcpml.example.forms.datasource;
import org.rcpml.core.datasource.DataSourceUtils;
import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.datasource.IDataSourceElementBinding;
import org.rcpml.core.datasource.IDataSourceFactory;
import org.w3c.dom.Node;


public class MergeDataSourceFactory implements IDataSourceFactory {
	private static class MergeDataSource implements IDataSource {
		private IDataSourceElementBinding last;
		public void bind(IDataSourceElementBinding object, String path) {
			if( last != null ) {
				DataSourceUtils.bindDataSourceElement(object, last);
			}
			last = object;
		}

		public String getName() {
			return "org.rcpml.example.forms.datasource.merge";
		}		
	}
	public IDataSource createDataSource(Node node) {
		return new MergeDataSource();
	}
}

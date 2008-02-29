package org.rcpml.core.datasource;


/**
 * This class represents binding with <code>IDataSource</code>
 * 
 * @author Yuri Strot
 */
public class DataBinding {
	
	private IDataSourceElementBinding object;
	private String path;
	
	public DataBinding(IDataSourceElementBinding object, String path) {
		this.object = object;
		this.path = path;
	}
	
	public IDataSourceElementBinding getObject() {
		return object;
	}
	
	public String getPath() {
		return path;
	}

}

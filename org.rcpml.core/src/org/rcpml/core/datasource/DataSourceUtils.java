package org.rcpml.core.datasource;

public class DataSourceUtils {
	/**
	 * Master value getted from original and setted to to 
	 */
	public static void bindDataSourceElement(
			IDataSourceElementBinding original,
			IDataSourceElementBinding to) {
		
			to.setValue( original.getValue() );
		
			original.addValueChangeListener(to);
			to.addValueChangeListener(original);
	}
}

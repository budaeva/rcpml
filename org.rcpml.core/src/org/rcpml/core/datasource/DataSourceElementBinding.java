package org.rcpml.core.datasource;


/**
 * This class are used to bind some value.
 * 
 * @author haiodo
 * 
 */
public class DataSourceElementBinding extends AbstractDataSourceElementBinding {	
	private Object fValue;
	private Object fType;

	public DataSourceElementBinding(Object value, Object type) {		
		this.fType = type;
		this.fValue = value;		
	}

	public Object getValue() {
		return this.fValue;
	}

	public Object getValueType() {
		return fType;
	}

	public void setValue(Object value) {
		if( this.fValue.equals((String)value)) {
			return;
		}	
		this.fValue = value;
		this.notifyValueChanged();
	}

	public void handleValueChange(IDataSourceElementBinding source) {
		if( this.fValue.equals((String)source.getValue())) {
			return;
		}
		this.fValue = source.getValue();
		this.notifyValueChanged();
	}	
}

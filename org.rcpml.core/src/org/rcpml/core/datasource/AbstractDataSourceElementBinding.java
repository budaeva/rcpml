package org.rcpml.core.datasource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractDataSourceElementBinding implements
		IDataSourceElementBinding {
	private List fChangeListeners = new ArrayList();
	
	public void addValueChangeListener(IDataSourceElementListener listener) {
		this.fChangeListeners.add(listener);
	}	

	public void removeValueChangeListener(IDataSourceElementListener listener) {
		this.fChangeListeners.remove(listener);
	}
	
	protected void notifyValueChanged( ) {
		Iterator i = this.fChangeListeners.iterator();
		while( i.hasNext()) {
			IDataSourceElementListener listener = (IDataSourceElementListener)i.next();
			listener.handleValueChange(this);
		}
	}
}

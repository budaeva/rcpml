package org.rcpml.core.datasource;

import org.rcpml.core.IController;
import org.rcpml.core.dom.DOMUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 * This class are used to bind DOM Node content. 
 * 
 * @author haiodo
 * @author Yuri Strot
 * 
 */
public class DataSourceElementContentBinding extends AbstractDataSourceElementBinding implements EventListener {
	private Node fNode;
	private String fValue;
	
	/**
	 * Do not allow infinite recursive binding
	 */
	private boolean ignoreEvents;

	public DataSourceElementContentBinding(Node node) {		
		fNode = node;
		fValue = DOMUtils.getChildrenAsText(node);
		initEventHandler();
	}
	
	private void initEventHandler() {
		Document doc = fNode.getOwnerDocument();		
		EventTarget et = (EventTarget) doc;

		et.addEventListener( IController.DOMSUBTREE_MODIFIED, this, true);
	}
	private void removeEventHandler() {
		Document doc = fNode.getOwnerDocument();		
		EventTarget et = (EventTarget) doc;

		et.addEventListener( IController.DOMSUBTREE_MODIFIED, this, true);
	}

	public Object getValue() {
		return fValue;
	}

	public Object getValueType() {
		return String.class;
	}

	public void setValue(Object value) {
		if (ignoreEvents) return;
		if( fValue.equals((String)value)) {
			return;
		}
		removeEventHandler();
		fValue = (String)value;
		set();
		initEventHandler();
		notifyValueChanged();
	}

	public void handleValueChange(IDataSourceElementBinding source) {
		if (ignoreEvents) return;
		if( fValue.equals((String)source.getValue())) {
			return;
		}
		removeEventHandler();
		fValue = (String)source.getValue();
		set();
		initEventHandler();
		notifyValueChanged();
	}
	public void handleEvent(Event event) {
		if (ignoreEvents) return;
		String value = DOMUtils.getChildrenAsText(fNode);
		if( fValue.equals(value)) {
			return;
		}
		fValue = value;
		notifyValueChanged();
	}
	
	private void set() {
		ignoreEvents = true;
		DOMUtils.setChildrenText(fNode, fValue);
		ignoreEvents = false;
	}
}

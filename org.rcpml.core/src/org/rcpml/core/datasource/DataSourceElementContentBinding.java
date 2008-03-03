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
	private Object fValue;
	private Object type;
	
	/**
	 * Do not allow infinite recursive binding
	 */
	private boolean ignoreEvents;

	public DataSourceElementContentBinding(Node node, Object type) {		
		fNode = node;
		fValue = DOMUtils.getChildrenAsText(node);
		this.type = type;
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
		return type;
	}
	
	private Object getValueFromString(String s) {
		if (type == int.class) {
			try {
				return new Integer(Integer.parseInt(s));
			}
			catch (Exception e) {
				return null;
			}
		}
		if (type == double.class) {
			try {
				return null;
			}
			catch (Exception e) {
				return new Double(0);
			}
		}
		return s;
	}

	public void setValue(Object value) {
		if (ignoreEvents) return;
		if( fValue.equals(value)) {
			return;
		}
		removeEventHandler();
		fValue = value;
		set();
		initEventHandler();
		notifyValueChanged();
	}

	public void handleValueChange(IDataSourceElementBinding source) {
		if (ignoreEvents) return;
		if( fValue.equals(source.getValue())) {
			return;
		}
		removeEventHandler();
		fValue = source.getValue();
		set();
		initEventHandler();
		notifyValueChanged();
	}
	public void handleEvent(Event event) {
		if (ignoreEvents) return;
		Object value = getValueFromString(DOMUtils.getChildrenAsText(fNode));
		if (value == null)
			return;
		if( fValue.equals(value)) {
			return;
		}
		fValue = value;
		notifyValueChanged();
	}
	
	private void set() {
		ignoreEvents = true;
		DOMUtils.setChildrenText(fNode, fValue.toString());
		ignoreEvents = false;
	}
}

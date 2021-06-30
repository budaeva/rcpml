package org.rcpml.core.datasource;

import org.rcpml.core.IController;
import org.rcpml.core.dom.DOMUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.Element;

/**
 * This class are used to bind DOM Node content. 
 * 
 * @author haiodo
 * @author Yuri Strot
 * 
 */
public class DataSourceElementContentBinding extends AbstractDataSourceElementBinding implements EventListener {
	protected Node fNode;
	protected Object fValue;
	private Object type;
	
	/**
	 * Do not allow infinite recursive binding
	 */
	protected boolean ignoreEvents;

	public DataSourceElementContentBinding(Node node, Object type) {		
		fNode = node;
		fValue = DOMUtils.getChildrenAsText(node);
		this.type = type;
		initEventHandler();
	}
	
	protected void initEventHandler() {
		Document doc = fNode.getOwnerDocument();		
		EventTarget et = (EventTarget) doc;

		et.addEventListener( IController.DOMSUBTREE_MODIFIED, this, true);
	}
	protected void removeEventHandler() {
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
	
	protected Object getValueFromString(String s) {
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

	public Node getfNode() {
		return fNode;
	}
}

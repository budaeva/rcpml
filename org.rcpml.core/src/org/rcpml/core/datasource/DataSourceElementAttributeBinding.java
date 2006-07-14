package org.rcpml.core.datasource;

import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.internal.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 * This class are used to bind some attribute of DOM Node.
 * 
 * @author haiodo
 * 
 */
public class DataSourceElementAttributeBinding extends AbstractDataSourceElementBinding implements EventListener {
	private Node fNode;
	private String fAttribute;
	private String fValue;

	public DataSourceElementAttributeBinding(Node node, String attribute) {		
		this.fNode = node;
		this.fAttribute = attribute;
		this.fValue = DOMUtils.getAttribute(node, this.fAttribute);
		initEventHandler();
	}
	private void initEventHandler() {
		Document doc = this.fNode.getOwnerDocument();		
		EventTarget et = (EventTarget) doc;

		et.addEventListener( Controller.DOMATTR_MODIFIED, this, true);		
	}
	private void removeEventHandler() {
		Document doc = this.fNode.getOwnerDocument();		
		EventTarget et = (EventTarget) doc;

		et.removeEventListener( Controller.DOMATTR_MODIFIED, this, true);
	}

	public Object getValue() {
		return this.fValue;
	}

	public Object getValueType() {
		return String.class;
	}

	public void setValue(Object value) {
		if( this.fValue.equals((String)value)) {
			return;
		}
		this.removeEventHandler();
		this.fValue = (String)value;
		DOMUtils.setAttribute(this.fNode, this.fAttribute, this.fValue );
		this.initEventHandler();
		this.notifyValueChanged();
	}

	public void handleValueChange(IDataSourceElementBinding source) {
		if( this.fValue.equals((String)source.getValue())) {
			return;
		}
		this.removeEventHandler();
		this.fValue = (String)source.getValue();
		DOMUtils.setAttribute(this.fNode, this.fAttribute, this.fValue );
		this.initEventHandler();
		this.notifyValueChanged();
	}
	public void handleEvent(Event event) {
		if( event.getType().equals(Controller.DOMATTR_MODIFIED)) {
			String value = DOMUtils.getAttribute(this.fNode, this.fAttribute);
			if( this.fValue.equals(value)) {
				return;
			}
			this.fValue = value;
			this.notifyValueChanged();
		}		
	}
}

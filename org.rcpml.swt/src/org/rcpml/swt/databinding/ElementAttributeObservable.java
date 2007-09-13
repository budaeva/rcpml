package org.rcpml.swt.databinding;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class ElementAttributeObservable extends AbstractObservableValue
		implements EventListener {
	private Node fNode;
	private String fValue;
	private String fAttribute;

	public ElementAttributeObservable(Node element, String attribute) {
		fNode = element;
		this.fAttribute = attribute;
		Document doc = element.getOwnerDocument();

		EventTarget et = (EventTarget) doc;

		et.addEventListener("DOMAttributeModified", this, true);
	}

	protected Object doGetValue() {
		return fValue;
	}

	public Object getValueType() {
		return String.class;
	}

	protected void doSetValue(Object value) {
		if (value instanceof String
				&& this.fNode.getNodeType() == Node.ELEMENT_NODE) {
			Element el = (Element) this.fNode;
			el.setAttribute(this.fAttribute, (String) value);
			this.fValue = (String) value;
		}
	}

	public void handleEvent(Event evt) {
		if (this.fNode.getNodeType() == Node.ELEMENT_NODE) {
			Element el = (Element) this.fNode;
			String newValue = el.getAttribute(this.fAttribute);
			fireValueChange(Diffs.createValueDiff(fValue, newValue));
		}
	}
}

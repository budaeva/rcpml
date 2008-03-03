package org.rcpml.swt.databinding;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.rcpml.core.IController;
import org.rcpml.core.dom.DOMUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class ElementTextObservable extends AbstractObservableValue implements EventListener {
	private Node fNode;
	private String fValue; 
	private boolean ignoreEvents = false;
	
	public ElementTextObservable( Node element) {
		fNode = element;
		Document doc = element.getOwnerDocument();
		
		EventTarget et = (EventTarget) doc;

		et.addEventListener( IController.DOMSUBTREE_MODIFIED, this, true);		
	}
	protected Object doGetValue() {
		return DOMUtils.getChildrenAsText(fNode);
	}

	public Object getValueType() {
		return String.class;
	}
	
	protected void doSetValue(Object value) {
		if (ignoreEvents) return;
		ignoreEvents = true;
		if( value instanceof String ) {
			DOMUtils.setChildrenText(this.fNode, (String)value );
			this.fValue = (String)value;
		}
		ignoreEvents = false;
	}
	public void handleEvent(Event evt) {
		if (ignoreEvents) return;
		String newValue = DOMUtils.getChildrenAsText(fNode);
		if ((newValue == null || fValue == null) ||
				!newValue.equals(fValue))
			fireValueChange(Diffs.createValueDiff(fValue, newValue ));
	}
}

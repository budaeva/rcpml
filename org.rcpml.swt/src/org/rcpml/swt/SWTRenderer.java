package org.rcpml.swt;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import org.rcpml.core.AbstractRenderer;
import org.rcpml.core.IController;
import org.rcpml.core.IRenderer;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SWTRenderer extends AbstractRenderer implements IRenderer {
	
	protected final static String ATTR_TEXT = "text";

	private interface IAttributeConverter {
		Object convert(SWTRenderer renderer, String attributeValue);

		Class getAttributeClass();
	}

	private final static class StringConverter implements IAttributeConverter {

		public Object convert(SWTRenderer renderer, String attributeValue) {
			return attributeValue;
		}

		public Class getAttributeClass() {
			return String.class;
		}

	}

	private static Map widgets;

	private static Map dynamicAttributes;

	static {
		widgets = new HashMap();
		widgets.put("button", Button.class);
		widgets.put("label", Label.class);

		dynamicAttributes = new HashMap();
		dynamicAttributes.put(ATTR_TEXT, new StringConverter());
	}

	/**
	 * Extract SWT style information from node attributes
	 * 
	 * @param node
	 * @return
	 */
	protected static int getSWTStyle(Node node) {
		return SWT.NONE;
	}
	
	protected SWTRenderer(IController controller) {
		super(controller);
	}

	protected final void setAttributes(Node node, Control control) {
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String attrName = attr.getNodeName();
			IAttributeConverter conv = (IAttributeConverter) dynamicAttributes
					.get(attrName);
			if (conv != null) {
				String methodName = "set"
						+ attrName.substring(0, 1).toUpperCase()
						+ attrName.substring(1);
				try {
					Method method = control.getClass().getMethod(methodName,
							new Class[] { conv.getAttributeClass() });
					method.invoke(control, new Object[] { conv.convert(this,
							attr.getNodeValue()) });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected final void setEventHandlers(Node node, Control control) {
		final String onMouseDown = getAttribute(node, "onmousedown");
		final String onMouseUp = getAttribute(node, "onmouseup");
		final String onDblClick = getAttribute(node, "ondblclick");
		if(onMouseDown != null || onMouseUp != null || onDblClick != null) {
			control.addMouseListener(new MouseListener() {

				public void mouseDoubleClick(MouseEvent e) {
					if(onDblClick!=null)
						getController().executeScript(onDblClick);
				}

				public void mouseDown(MouseEvent e) {
					if(onMouseDown!=null)
						getController().executeScript(onMouseDown);
				}

				public void mouseUp(MouseEvent e) {
					if(onMouseUp!=null)
						getController().executeScript(onMouseUp);
				}
				
			});
		}
	}
	
	private Control renderWidget(Composite target, Node node) {
		Class widgetClass = (Class) widgets.get(node.getNodeName());
		if(widgetClass == null)
			return null;
		try {
			Constructor ctor = widgetClass.getConstructor(new Class[] {
					Composite.class, Integer.TYPE });
			Control control = (Control) ctor.newInstance(new Object[] { target,
					new Integer(getSWTStyle(node)) });
			setAttributes(node, control);
			setEventHandlers(node, control);
			return control;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object renderNode(Node node, Object target) {
		switch (node.getNodeType()) {

		case Node.TEXT_NODE:
			Label label = new Label((Composite) target, SWT.NONE);
			label.setText(node.getNodeValue());
			return label;

		case Node.ELEMENT_NODE:
			return renderWidget((Composite) target, node);
		}
		return null;
	}

}

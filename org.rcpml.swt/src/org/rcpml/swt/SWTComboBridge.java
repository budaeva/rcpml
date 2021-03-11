/**
 * 
 */
package org.rcpml.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.rcpml.swt.databinding.ElementAttributeObservable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SWTComboBridge extends AbstractSWTBridge {
	private Combo fCombo;

	public SWTComboBridge(Node node, IController container) {
		super(node, container, false);
	}

	public Object getPresentation() {
		return this.fCombo;
	}

	protected void construct(Composite parent) {
		int style = SWT.BORDER | SWT.SINGLE | SWT.DROP_DOWN | SWT.READ_ONLY;

		this.fCombo = constructCombo(parent, style);

		this.fCombo.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}
		});
		DataBindingContext dbc = this.getBindingContext();

		dbc.bindValue(SWTObservables.observeSelection(this.fCombo),
				new ElementAttributeObservable(getNode(), "state"), null, null);

		update();
	}

	protected Combo constructCombo(Composite parent, int style) {
		return new Combo(parent, style);
	}

	public void update() {
		if (this.fCombo != null) {
			this.fCombo.setLayoutData(constructLayoutData(fCombo.getParent()));
//			this.fCombo.setText(DOMUtils.getChildrenAsText(this.getNode()));
			// Update texts
			Node node = this.getNode();
			NodeList childNodes = node.getChildNodes();
			List items = new ArrayList();
			int selection = 0;
			String state = this.getAttribute("state");
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) child;
					if (element.getTagName().equals("state")) {
						String value = element.getTextContent();
						if (value != null && value.length() > 0) {
							items.add(value);
							if (value.equals(state)) {
								selection = i;
							}
						}
					}
				}
			}
			String[] items2 = this.fCombo.getItems();

			String[] items3 = (String[]) items
					.toArray(new String[items.size()]);
			boolean gfound = true;
			for (int i = 0; i < items3.length; i++) {
				boolean found = false;
				for (int j = 0; j < items2.length; j++) {
					if (items2[j].equals(items3[i])) {
						found = true;
						break;
					}
				}
				if(!found) {
					gfound = false;
					break;
				}
			}
			if (!gfound) {
				this.fCombo.setItems(items3);
				this.fCombo.select(selection);
			}
		}
	}

	public void dispose() {
		if (this.fCombo != null) {
			this.fCombo.dispose();
			this.fCombo = null;
		}
	}
}

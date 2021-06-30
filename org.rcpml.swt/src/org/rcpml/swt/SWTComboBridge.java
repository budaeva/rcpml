/**
 * 
 */
package org.rcpml.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.datasource.DataBinding;
import org.rcpml.core.datasource.DataSourceElementAttributeBinding;
import org.rcpml.core.datasource.DataSourceElementContentBinding;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.swt.databinding.ElementAttributeObservable;
import org.rcpml.swt.databinding.ElementTextObservable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xored.scripting.core.IScriptContextManager;
import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

public class SWTComboBridge extends AbstractSWTBridge {
	private Combo fCombo;

	public SWTComboBridge(Node node, IController container) {
		super(node, container, false);
	}

	public Object getPresentation() {
		return this.fCombo;
	}

	protected void construct(Composite parent) {
		int style = SWT.BORDER | SWT.SINGLE | SWT.DROP_DOWN;

		this.fCombo = constructCombo(parent, style);
		update();

		initHandlers();
		
		DataBindingContext dbc = this.getBindingContext();

//		dbc.bindValue(SWTObservables.observeSelection(this.fCombo),
//				new ElementAttributeObservable(getNode(), "state"), null, null);
		
		dbc.bindValue(WidgetProperties.comboSelection().observe(fCombo), new ElementAttributeObservable(getNode(), "state"), null, null);
		
//		String state = getAttribute("state");
//		if (state != null && !state.equals("")) {
//			getController().bind(new DataBinding(
//					new DataSourceElementContentBinding(
//							getNode(), String.class), state));
//		}
		
		String path = getAttribute("path");
		if (path != null && !path.equals("")) {
			getController().bind(new DataBinding(
					new DataSourceElementAttributeBinding(getNode(), "state"), path));
		}

	}
	
	protected void initHandlers() {
		Combo combo = this.fCombo;
		
		combo.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent event) {
				String onChangeAction = getAttribute(RCPMLTagConstants.ONCHANGE_ATTR);
				if (onChangeAction.length() > 0) {
					IScriptContextManager manager = getController().getScriptManager();
					IScriptingContext context;
					try {
						context = manager.getContextFrom(onChangeAction);
					} catch (ScriptException e) {
						e.printStackTrace();
						context = null;
					}
					if (context != null) {
						context.bindObject("node", getNode());
						context.executeScript(onChangeAction);
					}
				}
			}
		});
		
		combo.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}
		});

	}


	protected Combo constructCombo(Composite parent, int style) {
		return new Combo(parent, style);
	}

	public void update() {
		if (this.fCombo != null) {
			int oldSelection = this.fCombo.getSelectionIndex();
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
			} 
			if (selection/2 != fCombo.getSelectionIndex() && !state.equals(""))
				this.fCombo.select(selection/2);
			String enabled = RCPCSSConstants.TRUE_VALUE;
			enabled = getAttribute(RCPMLTagConstants.ENABLED_ATTR);
			if (enabled != null) {
				if (enabled.equals(RCPCSSConstants.FALSE_VALUE)) {
					this.fCombo.setEnabled(false);
				} else {
					this.fCombo.setEnabled(true);
				}
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

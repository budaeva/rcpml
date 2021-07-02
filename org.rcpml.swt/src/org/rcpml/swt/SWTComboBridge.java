/**
 * 
 */
package org.rcpml.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.datasource.DataBinding;
import org.rcpml.core.datasource.DataSourceElementAttributeBinding;
import org.rcpml.core.internal.CorePlugin;
import org.rcpml.swt.databinding.ElementAttributeObservable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xored.scripting.core.IScriptContextManager;
import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

public class SWTComboBridge extends AbstractSWTBridge {
	private static final String PATH_ATTR = "path";
	private static final String STATE_ATTR = "state";
	
	private Combo fCombo;

	public SWTComboBridge(Node node, IController container) {
		super(node, container, false);
	}

	@Override
	public Object getPresentation() {
		return this.fCombo;
	}

	@Override
	protected void construct(Composite parent) {
		int style = SWT.BORDER | SWT.SINGLE | SWT.DROP_DOWN;

		this.fCombo = constructCombo(parent, style);
		update();

		initHandlers();
		
		DataBindingContext dbc = this.getBindingContext();
		
		dbc.bindValue(WidgetProperties.comboSelection().observe(fCombo), new ElementAttributeObservable(getNode(), STATE_ATTR), null, null);
				
		String path = getAttribute(PATH_ATTR);
		if (path != null && !path.equals("")) {
			getController().bind(new DataBinding(
					new DataSourceElementAttributeBinding(getNode(), STATE_ATTR), path));
		}

	}
	
	protected void initHandlers() {
		Combo combo = this.fCombo;
		
		combo.addModifyListener(event -> {
			String onChangeAction = getAttribute(RCPMLTagConstants.ONCHANGE_ATTR);
			if (onChangeAction != null && onChangeAction.length() > 0) {
				IScriptContextManager manager = getController().getScriptManager();
				IScriptingContext context;
				try {
					context = manager.getContextFrom(onChangeAction);
					if (context != null) {
						context.bindObject("node", getNode());
						context.executeScript(onChangeAction);
					}
				} catch (ScriptException e) {
					Status status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID, e.getMessage(), e);
					CorePlugin.getDefault().getLog().log(status);
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

	@Override
	public void update() {
		if (this.fCombo != null) {
			this.fCombo.setLayoutData(constructLayoutData(fCombo.getParent()));
			// Update texts
			Node node = this.getNode();
			NodeList childNodes = node.getChildNodes();
			List items = new ArrayList();
			int selection = 0;
			int elementI = 0;
			String state = this.getAttribute(STATE_ATTR);
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) child;
					if (element.getTagName().equals(STATE_ATTR)) {
						String value = element.getTextContent();
						if (value != null && value.length() > 0) {
							items.add(value);
							if (value.equals(state)) {
								selection = elementI;
							}
						}
					}
					elementI++;
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
			if (selection != fCombo.getSelectionIndex() && !state.equals(""))
				this.fCombo.select(selection);
			
			this.fCombo.setEnabled(this.getEnabled());
			
		}
	} 

	@Override
	public void dispose() {
		if (this.fCombo != null) {
			this.fCombo.dispose();
			this.fCombo = null;
		}
	}
}

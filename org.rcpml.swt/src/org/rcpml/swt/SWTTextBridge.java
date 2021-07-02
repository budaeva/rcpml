/**
 * 
 */
package org.rcpml.swt;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.datasource.DataBinding;
import org.rcpml.core.datasource.DataSourceElementContentBinding;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.core.internal.CorePlugin;
import org.rcpml.swt.databinding.ElementTextObservable;
import org.w3c.dom.Node;

import com.xored.scripting.core.IScriptContextManager;
import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

public class SWTTextBridge extends AbstractSWTBridge {
	
	private static final String PATH_ATTR = "path";

	private static final String EDITABLE_ID = "editable";

	private static final String MULTILINE = "multiline";

	protected Text fText;

	public SWTTextBridge(Node node, IController controller) {
		super(node, controller, false);
	}

	protected Text createText(Composite parent) {
		return new Text(parent, getStyle());
	}

	@Override
	protected void construct(Composite parent) {
		this.fText = this.createText(parent);
		update();

		initHandlers();

		DataBindingContext dbc = this.getBindingContext();
		
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(fText), new ElementTextObservable(getNode()), null, null);

		String path = getAttribute(PATH_ATTR);
		if (path != null && !path.equals("")) {
			getController().bind(new DataBinding(new DataSourceElementContentBinding(getNode(), String.class), path));
		}
	}

	protected void initHandlers() {
		Text text = this.fText;
		
		text.addModifyListener(event -> {
			String onChangeAction = getAttribute(RCPMLTagConstants.ONCHANGE_ATTR);
			if (onChangeAction != null && onChangeAction.length() > 0) {
				IScriptContextManager manager = getController().getScriptManager();
				IScriptingContext context;
				try {
					context = manager.getContextFrom(onChangeAction);
					if (context != null) {
						context.bindObject("node", getNode());
						context.bindObject("newValue", ((Text)(event.getSource())).getText());
						context.executeScript(onChangeAction);
					}
				} catch (ScriptException e) {
					Status status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID, e.getMessage(), e);
					CorePlugin.getDefault().getLog().log(status);
				}
			}
		});

		text.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}
		});

	}

	protected int getStyle() {
		int style = 0;
		RCPStylableElement stylable = (RCPStylableElement) getNode();

		String multiline = getAttribute(MULTILINE);
		if (TRUE_VALUE.equals(multiline)) {
			style |= SWT.MULTI;
		}

		Value wrapValue = ((RCPStylableElement) this.getNode()).getComputedValue(RCPCSSConstants.LAYOUT_WRAP_INDEX);
		if (TRUE_VALUE.equals(wrapValue.getStringValue())) {
			style |= SWT.WRAP;
		}

		Value borderValue = stylable.getComputedValue(RCPCSSConstants.LAYOUT_BORDER_INDEX);
		String border = borderValue.getStringValue();
		if (border.equals(RCPCSSConstants.LAYOUT_BORDER_VALUE)) {
			return style | SWT.BORDER;
		} else if (border.equals(RCPCSSConstants.LAYOUT_BORDER_SINGLE_VALUE)) {
			return style | SWT.SINGLE | SWT.BORDER;
		}
		return style;
	}

	@Override
	public Object getPresentation() {
		return this.fText;
	}

	@Override
	public void update() {
		if (this.fText != null) {
			this.fText.setLayoutData(this.constructLayoutData(this.fText.getParent()));

			String editable = TRUE_VALUE;
			editable = getAttribute(EDITABLE_ID);
			if (editable != null) {
				if (editable.equals(FALSE_VALUE)) {
					this.fText.setEditable(false);
				} else {
					this.fText.setEditable(true);
				}
			}

			this.fText.setEnabled(this.getEnabled());
		}
	}
	
	@Override
	public void dispose() {
		if( this.fText != null ) {
			this.fText.dispose();
			this.fText = null;
		}
	}
}
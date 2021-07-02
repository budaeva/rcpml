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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.datasource.DataBinding;
import org.rcpml.core.datasource.DataSourceElementContentBinding;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.core.internal.CorePlugin;
import org.rcpml.swt.databinding.ElementTextObservable;
import org.w3c.dom.Node;

import com.xored.scripting.core.IScriptContextManager;
import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

/**
 * @author Yuri Strot
 *
 */
public class SWTSpinnerBridge extends AbstractSWTBridge {
	
	
	private static final String EDITABLE_ID = "editable";

	private static final String PATH_ATTR = "path";
	
	private Spinner fSpinner;
	
	public SWTSpinnerBridge(Node node, IController container) {
		super( node, container, false );			
	}
	
	@Override
	public Object getPresentation() {
		return this.fSpinner;
	}
	@Override
	protected void construct( Composite parent ) {
		fSpinner = constructSpinner( parent, getStyle() );
		fSpinner.setMinimum(Integer.MIN_VALUE);
		fSpinner.setMaximum(Integer.MAX_VALUE);
		
		update();
		
		initHandlers();
		
		DataBindingContext dbc = this.getBindingContext();
		
		dbc.bindValue(WidgetProperties.widgetSelection().observe(this.fSpinner), new ElementTextObservable(
				getNode()), null, null );

		String path = getAttribute(PATH_ATTR);
		if (path != null && !path.equals("")) {
			getController().bind(new DataBinding(
					new DataSourceElementContentBinding(getNode(), int.class), path));
		}
	}
	
	protected void initHandlers() {
		Spinner spinner = this.fSpinner;
		
		spinner.addModifyListener(event -> {
			String onChangeAction = getAttribute(RCPMLTagConstants.ONCHANGE_ATTR);
			if (onChangeAction != null && onChangeAction.length() > 0) {
				IScriptContextManager manager = getController().getScriptManager();
				IScriptingContext context;
				try {
					context = manager.getContextFrom(onChangeAction);
					if (context != null) {
						context.bindObject("node", getNode());
						context.bindObject("newValue", ((Spinner)(event.getSource())).getText());
						context.executeScript(onChangeAction);
					}
				} catch (ScriptException e) {
					Status status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID, e.getMessage(), e);
					CorePlugin.getDefault().getLog().log(status);
				}
			}
		});
		
		spinner.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}
		});

	}
	
	protected int getStyle() {
		int style = SWT.BORDER;
		
		Value wrapValue = ((RCPStylableElement)this.getNode()).getComputedValue( RCPCSSConstants.LAYOUT_WRAP_INDEX );
		if( wrapValue.getStringValue().equals(TRUE_VALUE) )
			style |= SWT.WRAP;
		
		String editable = getAttribute(EDITABLE_ID);
		if( editable != null && editable.equals(FALSE_VALUE) )
			style |= SWT.READ_ONLY;

		return style;
	}
	
	protected Spinner constructSpinner( Composite parent, int style ) {
		return new Spinner(parent, style );
	}
	@Override
	public void update() {		
		if( this.fSpinner != null ) {
			this.fSpinner.setLayoutData( constructLayoutData(fSpinner.getParent() ));
			int value = 0;
			try {
				value = Integer.parseInt(DOMUtils.getChildrenAsText(this.getNode()));
			}
			catch (Exception e) {
			}
			if (this.fSpinner.getSelection() != value)
				this.fSpinner.setSelection(value);
			
			this.fSpinner.setEnabled(this.getEnabled());
		}
	}
	@Override
	public void dispose() {
		if( this.fSpinner != null ) {
			this.fSpinner.dispose();
			this.fSpinner = null;
		}
	}
}
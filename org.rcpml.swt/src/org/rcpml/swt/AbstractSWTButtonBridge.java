package org.rcpml.swt;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLTagConstants;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.datasource.DataBinding;
import org.rcpml.core.datasource.DataSourceElementContentBinding;
import org.rcpml.core.internal.CorePlugin;
import org.rcpml.swt.databinding.ElementTextObservable;
import org.w3c.dom.Node;

import com.xored.scripting.core.IScriptContextManager;
import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

public abstract class AbstractSWTButtonBridge extends AbstractSWTBridge {
	private Button fButton;	
	
	public static final String TITLE_ATTR = RCPMLTagConstants.TITLE_ATTR;	
	
	private static final String PATH_ATTR = "path";
	
	protected abstract int getStyle();	
	
	protected AbstractSWTButtonBridge(Node node, IController controller) {
		super(node, controller, false);		
	}
		
	protected String getTitle() {
		return this.getAttribute(TITLE_ATTR);
	}
	
	protected Button createButton( Composite parent, String title, int style ) {
		Button button = new Button(parent, style );
		button.setText(title);
		return button;
	}
	
	@Override
	protected void construct( Composite parent ) {
		// check initial values.
		int style = this.getStyle();
		String title = this.getTitle();
		this.fButton = this.createButton( parent, title, style );		
		
		update();
		
		initHandlers();

		DataBindingContext dbc = this.getBindingContext();
		
		dbc.bindValue(WidgetProperties.widgetSelection().observe(this.fButton), new ElementTextObservable(
				getNode()), null, null );

		String path = getAttribute(PATH_ATTR);
		if (path != null && !path.equals("")) {
			getController().bind(new DataBinding(
					new DataSourceElementContentBinding(
							getNode(), Boolean.class), path));
		}
		
	}
		
	protected void initHandlers() {
		Button button = this.getButton();							
		button.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				String onClickAction = getAttribute(RCPMLTagConstants.ONCLICK_ATTR);
				if( onClickAction != null && onClickAction.length() > 0 ) {
					IScriptContextManager manager = getController().getScriptManager();
					IScriptingContext context;
					try {
						context = manager.getContextFrom( onClickAction );
						if( context != null ) {
							context.bindObject("node", getNode() );
							context.bindObject("newValue", Boolean.toString(((Button)(arg0.getSource())).getSelection()));
							context.executeScript( onClickAction );
						}
					} catch (ScriptException e) {
						Status status = new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID, e.getMessage(), e);
						CorePlugin.getDefault().getLog().log(status);
					}
				}
			}					
		});		
		button.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}			
		});
	}
		
	@Override
	public Object getPresentation() {
		return this.fButton;
	}
	
	protected Button getButton() {
		return this.fButton;
	}
	@Override
	public void update() {
		if( this.fButton != null ) {
			this.fButton.setLayoutData( this.constructLayoutData(this.fButton.getParent() ) );
			
			String title = this.getTitle();
			if( title != null ) {
				this.fButton.setText(title);
			}
		
			this.fButton.setEnabled(this.getEnabled());
		
			this.fButton.update();
		}
	}
	@Override
	public void dispose() {
		if( this.fButton != null ) {
			this.fButton.dispose();
			this.fButton = null;
		}
	}
}

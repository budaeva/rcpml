package org.rcpml.swt;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IController;
import org.w3c.dom.Node;

public abstract class AbstractSWTButtonBridge extends AbstractSWTBridge {
	private Button fButton;	
	
	public static final String TITLE_ATTR = "title";	
	private static final String ENABLED_ATTR = "enabled";
	
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
	
	protected void construct( Composite parent ) {
		// check initial values.
		int style = this.getStyle();
		String title = this.getTitle();
		this.fButton = this.createButton( parent, title, style );
		
		this.fButton.setLayoutData( this.constructLayout(parent) );
		
		update();
		
		initHandlers();		
	}
		
	protected void initHandlers() {
		Button button = this.getButton();							
		button.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				String onClickAction = getAttribute("onclick");
				if( onClickAction.length() > 0 ) {
					getController().getScriptManager().executeScript( onClickAction );
				}
			}					
		});		
		button.addDisposeListener( new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeDataBinding();
			}			
		});
	}
		
	protected boolean getEnabled() {
		String enabled = this.getAttribute(ENABLED_ATTR);
		if( enabled.equals("false") ) {
			return false;
		}
		return true;
	}

	public Object getPresentation() {
		return this.fButton;
	}
	
	protected Button getButton() {
		return this.fButton;
	}
	public void update() {
//		String title = this.getTitle();
//		if( title != null ) {
//			this.fButton.setText(title);
//		}
		
		this.fButton.setEnabled(this.getEnabled());
		
		this.fButton.update();
	}
	public void dispose() {
		if( this.fButton != null ) {
			this.fButton.dispose();
			this.fButton = null;
		}
	}
}

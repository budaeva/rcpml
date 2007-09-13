package org.rcpml.tools.ui.internal.browser;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;
import org.rcpml.core.IController;
import org.rcpml.core.IRCPMLConstructor;
import org.rcpml.core.RCPML;
import org.rcpml.core.internal.RCPMLImpl;
import org.w3c.dom.Document;

public class RCPMLBrowser extends Composite {
	private Composite rcpmlPane;
	private Combo address;
	private Button refresh;
	private Label status;

	public RCPMLBrowser(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void createControl() {
		this.setLayout(new GridLayout(3, false));
		Label l = new Label(this, SWT.NONE);
		l.setText("Address");
		this.address = new Combo(this, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN);
		this.address.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true,
				false));

		this.refresh = new Button(this, SWT.PUSH);
		this.refresh.setText("Refresh");

		rcpmlPane = new Composite(this, SWT.BORDER);
		rcpmlPane.setLayout(new FillLayout());
		GridData paneLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		paneLayoutData.horizontalSpan = 3;
		rcpmlPane.setLayoutData(paneLayoutData);
		status = new Label(this, SWT.NONE);
		GridData d = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		d.horizontalSpan = 3;
		status.setLayoutData(d);

		initActions();
	}

	private void initActions() {
		this.refresh.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				refreshPane();
			}
		});
		this.address.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.TRAVERSE_RETURN) {
					refreshPane();
				}
			}
		});
	}

	private void refreshPane() {
		URI uri = getURI();
		if (uri == null) {
			return;
		}
		Document document;
		try {
			document = RCPMLImpl.getDocument(uri.toString());
		} catch (CoreException e1) {
			status.setText("Error to locate document");
			e1.printStackTrace();
			return;
		}
		renderDocument(document);
		status.setText("Could not create constructor");

		status.setText("");
	}

	public class BrowserSupport {
		RCPMLBrowser browser;
		public BrowserSupport(RCPMLBrowser b) {
			this.browser = b;
		}
		public void navigate(String uri) {
			try {
				Document document = RCPMLImpl.getDocument(uri.toString());
				navigate(uri, document);
			} catch (Throwable e) {
				// Try to load document from previous location
				try {
					URI u = new URI(uri);
					if( u.getScheme() != null ) {
						return;
					}
				} catch (URISyntaxException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				URI prevURI = this.browser.getURI();
				IPath path = new Path(prevURI.getPath());
				try {
					URI newUri = new URI(prevURI.getScheme(), path
							.removeLastSegments(1).append(new Path(uri))
							.toString(), (String) null);
					Document document = RCPMLImpl.getDocument(newUri.toString());
					this.navigate(newUri.toString(), document);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CoreException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		private void navigate(String uri, Document document) {
//			this.browser.address.setText(uri.toString());
//			this.browser.renderDocument(document);
			OpenRCPMLBrowserEditorAction a = new OpenRCPMLBrowserEditorAction(uri);
			a.run(null);
		}
		public void call() {
			System.out.println("coo;");
		}
	}

	BrowserSupport support = new BrowserSupport(this);

	private void renderDocument(Document document) {
		// Dispose all early added childrens.
		Control[] children = rcpmlPane.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].dispose();
		}
		
		IController bridgeController = RCPML.createWithConstructor(document);
		if (bridgeController == null) {
			status.setText("Could not create rcpml");
			return;
		}
		IRCPMLConstructor constructor = bridgeController.getRootBridge();
		if (constructor != null) {
			bridgeController.getScriptManager().addDefaultBinding("viewer",
					support);
			Object child = constructor
					.createInstance(new Object[] { this.rcpmlPane });
			if (child == null) {
				status.setText("Could not create rcpml");
				return;
			}
			storeCombo();
		} else {
			bridgeController = RCPML.create(document);
			if (bridgeController == null) {
				status.setText("Could not create rcpml");
				return;
			}
			bridgeController.getScriptManager().addDefaultBinding("viewer",
					support);
			Object child = bridgeController.getPresentation();
			if (child instanceof ViewPart) {
				((ViewPart) child).createPartControl(this.rcpmlPane);
			} else if (child instanceof EditorPart) {
				((EditorPart) child).createPartControl(this.rcpmlPane);
			}
			this.rcpmlPane.layout();
			storeCombo();
			return;
		}
	}

	private void storeCombo() {
		String value = this.address.getText();
		String[] items = this.address.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(value)) {
				return;
			}
		}
		this.address.add(value);
	}

	private URI getURI() {
		try {
			return new URI(this.address.getText());
		} catch (URISyntaxException e) {
			status.setText("Error:Incorrect URI");
			// e.printStackTrace();
		}
		return null;
	}

	public void renderFile(IFile file) {
		this.address.setText(file.getFullPath().makeAbsolute().toOSString());
		refreshPane();
	}
	public void navigate(String uri) {
		this.address.setText(uri);
		refreshPane();
	}
}

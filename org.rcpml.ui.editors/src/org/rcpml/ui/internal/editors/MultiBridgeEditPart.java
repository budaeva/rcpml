package org.rcpml.ui.internal.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.rcpml.swt.ICompositeHolder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.xored.scripting.core.ScriptException;

class MultiBridgeEditPart extends MultiPageEditorPart implements
		ICompositeHolder, IBridgeEditorPart {
	private Composite fComposite;
	private ScrolledComposite fTextComposite;
	private Text fText = null;

	EditorBridge bridge;

	private boolean fInitialized = false;

	public MultiBridgeEditPart(EditorBridge bridge) {
		super();
		this.bridge = bridge;
	}

	protected void createPages() {
		fComposite = new Composite(this.getContainer(), SWT.NONE);
		this.fComposite.setLayout(new FillLayout());
		this.bridge.build();
		fInitialized = true;
		
		this.addPage(fComposite);
		this.setPageText(0, "RCPML Editor");
		
		try {
			this.bridge.getController().getScriptManager().getDefaultContext();
		} catch (ScriptException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
		this.bridge.executeInitScript( this );
		
		this.fTextComposite = new ScrolledComposite(this.getContainer(), SWT.H_SCROLL | SWT.V_SCROLL);
//		this.fTextComposite.setLayout(new FillLayout());
		this.fText = new Text(this.fTextComposite, SWT.MULTI);
		this.fTextComposite.setContent(this.fText);
		this.addPage(this.fTextComposite);
		this.setPageText(1, "RCPML DOM presentation");
		
		this.getContainer().addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void controlResized(ControlEvent e) {
				if( fText != null ) {
					updateText();
				}
			}
		});
	}

	public boolean isInitialized() {
		return fInitialized;
	}

	public void setFocus() {
		this.fComposite.setFocus();
	}

	public Composite getComposite() {
		return this.fComposite;
	}

	public void dispose() {
		bridge.disposeBridge();
	}

	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);							
		try {
			this.bridge.getController().getScriptManager().getDefaultContext().bindObject("editorInput", input );
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		Control c = this.getControl(newPageIndex);
		if( c.equals(this.fTextComposite)) {
			updateText();
		}
	}
	private void printNode( int indent, Node node, StringBuffer buffer ) {
		switch(node.getNodeType()) {
		case Node.ELEMENT_NODE:
			addIndent( buffer, indent );
			buffer.append("<" + node.getNodeName() + " " + attrsToString( node.getAttributes() ) + ">\n");
			break;
		case Node.TEXT_NODE:
			break;
		case Node.ATTRIBUTE_NODE:
			break;
		}
		for( Node child = node.getFirstChild(); child != null; child = child.getNextSibling() ) {
			printNode( indent + 1, child, buffer );
		}
		
		switch(node.getNodeType()) {
		case Node.ELEMENT_NODE:
			addIndent( buffer, indent );
			buffer.append("</" + node.getNodeName() + ">\n");
			break;
		case Node.TEXT_NODE:
			break;
		case Node.ATTRIBUTE_NODE:
			break;
		}
	}
	
	private String attrsToString(NamedNodeMap attributes) {
		StringBuffer buf = new StringBuffer();
		for( int i = 0; i < attributes.getLength(); ++i ) {
			Attr attr = (Attr)attributes.item(i);
			buf.append(attr.getName() + "=\"" + attr.getValue() + "\"" );
			if( i != attributes.getLength()-1 ) {
				buf.append(" ");
			}
		}
		return buf.toString();
	}

	private void addIndent(StringBuffer buffer, int indent) {
		for( int i = 0; i < indent; ++i ) {
			buffer.append("\t");
		}
	}

	private void updateText() {
		Document doc = this.bridge.getController().getDocument();
		StringBuffer buff = new StringBuffer();
		printNode(0, doc.getFirstChild(), buff);
		this.fText.setText(buff.toString());
		Point s = this.fText.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Point s2 = this.getContainer().getSize();
		if( s.x < s2.x ) {
			s.x = s2.x;
		}
		if( s.y < s2.y ) {
			s.y = s2.y;
		}
		this.fText.setSize(s);
	}	
	
}
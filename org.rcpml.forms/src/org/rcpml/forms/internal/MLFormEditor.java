package org.rcpml.forms.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

//public class MLFormEditor { extends FormEditor {
//	
//	private final static String EVENT_SET_INPUT = "onsetinput";
//	
//	private final static class SetEditorInputEvent implements Event {
//		
//		private EventTarget node;
//		
//		private SetEditorInputEvent(EventTarget target) {
//			node = target;
//		}
//
//		public boolean getBubbles() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		public boolean getCancelable() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		public EventTarget getCurrentTarget() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		public short getEventPhase() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public EventTarget getTarget() {
//			return node;
//		}
//
//		public long getTimeStamp() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public String getType() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		public void initEvent(String arg0, boolean arg1, boolean arg2) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void preventDefault() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void stopPropagation() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
//	
//	private Node node;
//	private FormsRenderer renderer;
//
//	MLFormEditor(Node node, FormsRenderer renderer) {
//		this.node = node;
//		this.renderer = renderer;
//	}
//	
//	protected FormToolkit createToolkit(Display display) {
//		return renderer.getToolkit(display);
//	}
//
//	protected void addPages() {
//		for(Node n = node.getFirstChild();n != null;n = n.getNextSibling()) {
//			Object child = renderer.renderNode(n, this);
//			if(child instanceof Control) {
//				addPage((Control) child);
//			} else if(child instanceof IFormPage) {
//				try {
//					addPage((IFormPage) child);
//				} catch(PartInitException pie) {
//					pie.printStackTrace();
//					throw new RCPMLException(pie.getMessage());
//				}
//			}
//		}
//	}
//
//	public void doSave(IProgressMonitor monitor) {
//	}
//
//	public void doSaveAs() {
//	}
//
//	public boolean isSaveAsAllowed() {
//		return false;
//	}
//
//	protected void setInput(IEditorInput input) {
//		super.setInput(input);
//		//renderer.fireEvent(node, EVENT_SET_INPUT, new SetEditorInputEvent((EventTarget) node));
//	}

//}

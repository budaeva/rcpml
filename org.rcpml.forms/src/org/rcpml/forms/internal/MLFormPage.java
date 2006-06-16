//package org.rcpml.forms.internal;
//
//import org.eclipse.ui.forms.IManagedForm;
//import org.eclipse.ui.forms.editor.FormEditor;
//import org.eclipse.ui.forms.editor.FormPage;
//import org.eclipse.ui.forms.widgets.ScrolledForm;
//import org.w3c.dom.Node;
//
//public class MLFormPage extends FormPage {
//	
//	private Node node;
//	private FormsRenderer renderer;
//
//	public MLFormPage(FormEditor editor, String id, String title) {
//		super(editor, id, title);
//	}
//
//	public MLFormPage(String id, String title) {
//		super(id, title);
//	}
//
//	void setRenderData(Node node, FormsRenderer renderer) {
//		this.node = node;
//		this.renderer = renderer;
//	}
//
//	protected void createFormContent(IManagedForm managedForm) {
//		ScrolledForm form = managedForm.getForm();
//		renderer.render(node, form);
//	}		
//}

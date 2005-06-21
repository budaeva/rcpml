package org.rcpml.forms.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.rcpml.core.IController;
import org.rcpml.core.IRenderer;
import org.rcpml.core.RCPMLException;
import org.rcpml.swt.SWTRenderer;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;

public class FormsRenderer extends SWTRenderer implements IRenderer {

	private final static String ID_ATTR = "id";
	private final static String TITLE_ATTR = "title";

	private FormToolkit toolkit;

	FormsRenderer(IController controller) {
		super(controller);
	}
	
	void fireEvent(Node node, String eventName, Event event) {
		getController().setEvent(event);
		getController().executeScript(getAttribute(node, eventName));
	}

	FormToolkit getToolkit(Display display) {
		if (toolkit == null) {
			toolkit = new FormToolkit(new FormColors(display));
		}
		return toolkit;
	}

	private FormToolkit getToolkit() {
		if (toolkit != null)
			return toolkit;
		return getToolkit(PlatformUI.getWorkbench().getDisplay());
	}

	void render(Node node, Form form) {
		setAttributes(node, form);
		TableWrapLayout layout = new TableWrapLayout();
		form.getBody().setLayout(layout);
		renderNodeChildren(node, form.getBody());
	}

	void render(Node node, ScrolledForm form) {
		setAttributes(node, form);
		TableWrapLayout layout = new TableWrapLayout();
		form.getBody().setLayout(layout);
		renderNodeChildren(node, form.getBody());
	}
	
	/**
	 * Only one child allowed.
	 * 
	 * @param node
	 * @param target
	 * @return
	 */
	private Object renderChild(Node node, Object target) {
		Object result = null;
		for(Node n=node.getFirstChild(); n != null; n = n.getNextSibling()) {
			Object rendered = getController().renderNode(n, target);
			if (rendered != null) {
				if (result != null)
					throw new RCPMLException("Only one child allowed");
				result = rendered;
			}
		}
		return result;
	}
	
	private static int countChildren(Node node, short type) {
		int result = 0; 
		for(Node n=node.getFirstChild(); n != null; n = n.getNextSibling()) 
			if(n.getNodeType() == type) ++result;
		return result;
	}

	public Object renderNode(Node node, Object target) {
		String name = node.getLocalName();
		if ("form".equals(name)) {
			Form form = getToolkit().createForm((Composite) target);
			render(node, form);
			return form;
		}
		if ("hyperlink".equals(name)) {
			return getToolkit().createHyperlink((Composite) target,
					getAttribute(node, ATTR_TEXT), SWT.WRAP);
		}
		if ("editor".equals(name)) {
			return new MLFormEditor(node, this);
		}
		if ("page".equals(name)) {
			String id = getAttribute(node, ID_ATTR);
			String title = getAttribute(node, TITLE_ATTR);
			MLFormPage formPage = new MLFormPage((FormEditor) target, id, title);
			formPage.setRenderData(node, this);
			return formPage;
		}
		if ("section".equals(name)) {
			Section section = getToolkit().createSection((Composite) target,
					Section.TWISTIE | Section.TITLE_BAR);
			// TODO set section description
			setAttributes(node, section);
			Object client = renderChild(node, section);
			section.setClient((Control) client);
			return section;
		}
		if ("hbox".equals(name)) {
			Composite hbox = getToolkit().createComposite((Composite) target, SWT.WRAP);
			GridLayout layout = new GridLayout();
			layout.numColumns = countChildren(node, Node.ELEMENT_NODE);
			hbox.setLayout(layout);
			renderNodeChildren(node, hbox);
			return hbox;
		}
		if ("button".equals(name)) {
			Button button = getToolkit().createButton((Composite) target,
					getAttribute(node, ATTR_TEXT), SWT.PUSH);
			return button;
		}
		if ("table".equals(name)) {
			Table table = getToolkit().createTable((Composite) target, SWT.NULL);
			return table;
		}
		return null;
	}

}

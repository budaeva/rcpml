package org.ecpml.emf.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.ui.IFileEditorInput;
import org.rcpml.core.IController;
import org.rcpml.core.datasource.DataSourceUtils;
import org.rcpml.core.datasource.IDataSourceElementBinding;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.xored.scripting.core.ScriptException;

/**
 * @author Yuri Strot
 *
 */
public class EMFLoader {
	
	private static EMFLoader current;
	
	public static EMFLoader getCurrent() {
		return current;
	}
	
	private IController container;
	private int groupId = 0;
	private Resource resource;
	
	private List bindings = new ArrayList();
	
	private class Binding {
		String eObject;
		String eFeature;
		IDataSourceElementBinding object;
		
		public Binding(String eObject, String feature,
				IDataSourceElementBinding object) {
			super();
			this.eObject = eObject;
			this.eFeature = feature;
			this.object = object;
		}
		
	}
	
	public EMFLoader(IController container) {
		this.container = container;
		current = this;
	}
	
	public void addBinding(String eObject, String eFeature, IDataSourceElementBinding object) {
		Binding binding = new Binding(eObject, eFeature, object);
		if (resource != null)
			addBinding(binding);
		else
			bindings.add(binding);
	}
	
	private void addBinding(Binding binding) {
		EObject obj = resource.getEObject(binding.eObject);
		if (obj != null) {
			Iterator attrs = obj.eClass().getEAllStructuralFeatures().iterator();
			while (attrs.hasNext()) {
				EStructuralFeature feature = (EStructuralFeature) attrs.next();
				if (feature.getName().equals(binding.eFeature)) {
					DataSourceUtils.bindDataSourceElement(new EMFDataSourceElementBinding(
							obj, feature, feature.getEType().getInstanceClass()), binding.object);
					break;
				}
			}
		}
	}
	
	private AdapterFactoryEditingDomain getEditDomain() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		
		BasicCommandStack commandStack = new BasicCommandStack();
		
		return new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap());
	}
	
	public Resource getResource(IFileEditorInput modelFile) {
		
		AdapterFactoryEditingDomain editingDomain = getEditDomain();
		//
		URI resourceURI = URI.createPlatformResourceURI(modelFile.getFile().getFullPath().toString(), true);
		Resource resource = null;
		try {
			// Load the resource through the editing domain.
			//
			resource = editingDomain.getResourceSet().getResource(resourceURI, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			resource = editingDomain.getResourceSet().getResource(resourceURI, false);
		}
		return resource;
	}
	
	private void updateBindings() {
		Iterator it = bindings.iterator();
		while (it.hasNext()) {
			Binding elem = (Binding) it.next();
			addBinding(elem);
		}
	}
	
	public void readObject(Object o) {
		if (o instanceof IFileEditorInput) {
			IFileEditorInput input = (IFileEditorInput)o;
			resource = getResource(input);
			updateBindings();
			Iterator it = resource.getContents().iterator();
			HashSet set = new HashSet();
			while (it.hasNext()) {
				EObject object = (EObject) it.next();
				add(object, set);
			}
		}
	}
	
	private void add(EObject object, HashSet set) {
		if (set.contains(object))
			return;
		set.add(object);
		String id = "group" + groupId++;
		addGroup(id);
		Iterator attrs = object.eClass().getEAllStructuralFeatures().iterator();
		addLabel("ID", getHumanReadableValue(object), id);
		while (attrs.hasNext()) {
			EStructuralFeature feature = (EStructuralFeature) attrs.next();
			Object value = object.eGet(feature);
			if (value instanceof List) {
				addCombo(feature.getName(), ((List)value).toArray(), id);
				Iterator it = ((List)value).iterator();
				while (it.hasNext()) {
					Object elem = (Object) it.next();
					if (elem instanceof EObject) {
						add((EObject)elem, set);
					}
				}
			}
			else if (value instanceof EObject) {
				add(object, set);
			}
			else if (feature != null && value != null){
				if (value instanceof Integer) {
					addSpinner(feature.getName(), value.toString(), id);
				}
				else {
					String path = resource.getURIFragment(object) + "/" + feature.getName();
					addText(feature.getName(), value.toString(), id, path);
					System.out.println(path);
				}
			}
		}
	}
	
	private void addCombo(String name, Object[] values, String id) {
		Document doc = container.getDocument();
		Element parent = doc.getElementById(id);
		
		Element label = doc.createElementNS("http://rcpml.org/forms", "label");
		Text labelValue = doc.createTextNode(name);
		label.appendChild(labelValue);
		parent.appendChild(label);
		
		Element combo = doc.createElementNS("http://rcpml.org/forms", "combo");
		for (int i = 0; i < values.length; i++) {
			Element state = doc.createElementNS("http://rcpml.org/forms", "state");
			Text value = doc.createTextNode(getHumanReadableValue(values[i]));
			state.appendChild(value);
			combo.appendChild(state);
		}
		parent.appendChild(combo);
	}
	
	private String getHumanReadableValue(Object obj) {
		if (obj instanceof EObject) {
			EObject eobj = (EObject)obj;
			return eobj.getClass().getSimpleName()
				+ "@" + eobj.hashCode();
		}
		return obj.toString();
	}
	
	private void addGroup(String id) {
		try {
			container.getScriptManager().executeScript(
					"addGroup('" + id + "')");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	private void addLabel(String name, String value, String id) {
		try {
			container.getScriptManager().executeScript("addLabel('" + 
					name + ":', '" + value + "', '" + id + "')");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	private void addSpinner(String name, String value, String id) {
		try {
			container.getScriptManager().executeScript("addSpinner('" + 
					name + ":', '" + value + "', '" + id + "')");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	private void addText(String name, String value, String id, String path) {
		try {
			container.getScriptManager().executeScript("addText('" + 
					name + ":', '" + value + "', '" + id + "', '" + path + "')");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

}

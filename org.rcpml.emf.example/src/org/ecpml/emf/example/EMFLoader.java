package org.ecpml.emf.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
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
import org.rcpml.emf.example.synch.FileListener;
import org.rcpml.emf.example.synch.IFileChangeListener;
import org.rcpml.swt.ICompositeHolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.xored.scripting.core.ScriptException;

/**
 * @author Yuri Strot
 *
 */
public class EMFLoader implements IFileChangeListener {
	
	private static EMFLoader current;
	
	public static EMFLoader getCurrent() {
		return current;
	}
	
	private IController container;
	private int groupId = 0;
	private Resource resource;
	
	private List bindings = new ArrayList();
	
	private Map dsBindings = new HashMap();
	private IFile file;
	
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
	
	public File getFile() {
		return file.getLocation().toFile();
	}
	
	public void fileChanged() {
		updateResource();
		Set bindings = dsBindings.keySet();
		Iterator ut = bindings.iterator();
		while (ut.hasNext()) {
			Binding binding = (Binding) ut.next();
			EMFDataSourceElementBinding dsBinding = 
				(EMFDataSourceElementBinding)dsBindings.get(binding);
			addBinding(binding, dsBinding);
		}
		if (container.getPresentation() instanceof ICompositeHolder) {
			ICompositeHolder holder = (ICompositeHolder)container.getPresentation();
			holder.getComposite().getDisplay().asyncExec(new Runnable() {
				
				public void run() {
					Collection bindings = dsBindings.values();
					Iterator ut = bindings.iterator();
					while (ut.hasNext()) {
						EMFDataSourceElementBinding binding = 
							(EMFDataSourceElementBinding) ut.next();
						binding.update();
					}
				}
				
			});
		}
	}
	
	public void fileRemoved() {
		dsBindings.clear();
	}
	
	public EMFLoader(IController container) {
		this.container = container;
		current = this;
	}
	
	public void addBinding(String eObject, String eFeature, IDataSourceElementBinding object) {
		Binding binding = new Binding(eObject, eFeature, object);
		if (resource != null)
			createBinding(binding);
		else
			bindings.add(binding);
	}
	
	private void addBinding(Binding binding, EMFDataSourceElementBinding dsBinding) {
		EObject obj = resource.getEObject(binding.eObject);
		if (obj != null) {
			Iterator attrs = obj.eClass().getEAllStructuralFeatures().iterator();
			while (attrs.hasNext()) {
				EStructuralFeature feature = (EStructuralFeature) attrs.next();
				if (feature.getName().equals(binding.eFeature)) {
					dsBinding.setAll(obj, feature, feature.getEType().getInstanceClass());
				}
			}
		}
	}
	
	private void createBinding(Binding binding) {
		EMFDataSourceElementBinding dsBinding = new EMFDataSourceElementBinding();
		addBinding(binding, dsBinding);
		dsBindings.put(binding, dsBinding);
		if (dsBinding != null) {
			DataSourceUtils.bindDataSourceElement(dsBinding, binding.object);
		}
	}
	
	private AdapterFactoryEditingDomain getEditDomain() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		
		BasicCommandStack commandStack = new BasicCommandStack();
		
		return new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap());
	}
	
	public void updateFile(IFileEditorInput modelFile) {
		
		file = modelFile.getFile();
		
		FileListener.getInstance().removeListener(this);
		FileListener.getInstance().addListener(this);
		
		updateResource();
	}
	
	private void updateResource() {
		AdapterFactoryEditingDomain editingDomain = getEditDomain();
		
		URI resourceURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		Resource resource = null;
		try {
			resource = editingDomain.getResourceSet().getResource(resourceURI, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			resource = editingDomain.getResourceSet().getResource(resourceURI, false);
		}
		this.resource = resource;
	}
	
	private void updateBindings() {
		Iterator it = bindings.iterator();
		while (it.hasNext()) {
			Binding elem = (Binding) it.next();
			createBinding(elem);
		}
	}
	
	public void readObject(Object o) {
		if (o instanceof IFileEditorInput) {
			IFileEditorInput input = (IFileEditorInput)o;
			updateFile(input);
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
		//addGroup(id);
		Iterator attrs = object.eClass().getEAllStructuralFeatures().iterator();
		//addLabel("ID", getHumanReadableValue(object), id);
		while (attrs.hasNext()) {
			EStructuralFeature feature = (EStructuralFeature) attrs.next();
			Object value = object.eGet(feature);
			if (value instanceof List) {
				//addCombo(feature.getName(), ((List)value).toArray(), id);
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
					//addSpinner(feature.getName(), value.toString(), id);
				}
				else {
					String path = resource.getURIFragment(object) + "/" + feature.getName();
					//addText(feature.getName(), value.toString(), id, path);
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

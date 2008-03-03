package org.ecpml.emf.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.IFileEditorInput;
import org.rcpml.core.IController;
import org.rcpml.core.datasource.DataSourceUtils;
import org.rcpml.core.datasource.IDataSourceElementBinding;
import org.rcpml.emf.example.synch.EMFSynchronizator;
import org.rcpml.swt.ICompositeHolder;

/**
 * @author Yuri Strot
 *
 */
public class EMFBindingManager extends EMFSynchronizator {
	
	public EMFBindingManager(IController container) {
		this.container = container;
	}
	
	public void setInput(IFileEditorInput input) {
		super.setInput(input);
		createDataBindings();
	}
	
	public void fileChanged() {
		super.fileChanged();
		Set bindings = dsBindings.keySet();
		Iterator ut = bindings.iterator();
		while (ut.hasNext()) {
			Binding binding = (Binding) ut.next();
			EMFDataSourceElementBinding dsBinding = 
				(EMFDataSourceElementBinding)dsBindings.get(binding);
			updateBinding(binding, dsBinding);
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
	
	public void addBinding(String eObject, String eFeature, IDataSourceElementBinding object) {
		Binding binding = new Binding(eObject, eFeature, object);
		if (getResource() != null)
			createBinding(binding);
		else
			bindings.add(binding);
	}
	
	private void updateBinding(Binding binding, EMFDataSourceElementBinding dsBinding) {
		EObject obj = getResource().getEObject(binding.eObject);
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
		updateBinding(binding, dsBinding);
		dsBindings.put(binding, dsBinding);
		if (dsBinding != null) {
			DataSourceUtils.bindDataSourceElement(dsBinding, binding.object);
		}
	}
	
	private void createDataBindings() {
		Iterator it = bindings.iterator();
		while (it.hasNext()) {
			Binding elem = (Binding) it.next();
			createBinding(elem);
		}
	}
	
	private class Binding {
		private String eObject;
		private String eFeature;
		private IDataSourceElementBinding object;
		
		public Binding(String eObject, String feature,
				IDataSourceElementBinding object) {
			this.eObject = eObject;
			this.eFeature = feature;
			this.object = object;
		}
		
		public String getEFeature() {
			return eFeature;
		}
		
		public String getEObject() {
			return eObject;
		}
		
		public IDataSourceElementBinding getObject() {
			return object;
		}
		
	}
	
	private IController container;
	
	private List bindings = new ArrayList();
	private Map dsBindings = new HashMap();

}

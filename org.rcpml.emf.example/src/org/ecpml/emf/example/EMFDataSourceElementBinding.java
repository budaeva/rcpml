package org.ecpml.emf.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.rcpml.core.datasource.AbstractDataSourceElementBinding;
import org.rcpml.core.datasource.IDataSourceElementBinding;

/**
 * @author Yuri Strot
 *
 */
public class EMFDataSourceElementBinding extends AbstractDataSourceElementBinding {
	
	private EObject obj;
	private EStructuralFeature feature;
	
	public EMFDataSourceElementBinding(EObject obj, EStructuralFeature feature) {
		this.obj = obj;
		this.feature = feature;
	}

	public Object getValue() {
		return obj.eGet(feature);
	}

	public Object getValueType() {
		return String.class;
	}

	public void setValue(Object value) {
		obj.eSet(feature, value);
		save();
		notifyValueChanged();
	}
	
	private void save() {
		final Map saveOptions = new HashMap();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		
		try {
			obj.eResource().save(saveOptions);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleValueChange(IDataSourceElementBinding source) {
		setValue(source.getValue());
	}

}

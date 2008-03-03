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
	private Class type;
	
	public EMFDataSourceElementBinding() {
	}
	
	public void setAll(EObject obj, EStructuralFeature feature, Class type) {
		this.obj = obj;
		this.feature = feature;
		this.type = type;
	}

	public Object getValue() {
		return obj.eGet(feature);
	}

	public Object getValueType() {
		return type;
	}

	public void setValue(Object value) {
		changeValue(value);
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
	
	public void update() {
		notifyValueChanged();
	}

	public void handleValueChange(IDataSourceElementBinding source) {
		changeValue(source.getValue());
	}
	
	private void changeValue(Object value) {
		try {
			obj.eSet(feature, convertValue(value));
		}
		catch (Exception e) {
			e.printStackTrace();
			//if value do not valid - ignore it
		}
		save();
	}
	
	private Object convertValue(Object value) {
		if (value == null)
			return null;
		if (value.getClass() == type) {
			return value;
		}
		if (value instanceof String) {
			String s = (String)value;
			if (type == int.class) {
				return new Integer(Integer.parseInt(s));
			}
			if (type == double.class) {
				return new Double(Double.parseDouble(s));
			}
			if (type == boolean.class) {
				return new Boolean(Boolean.parseBoolean(s));
			}
			if (type == long.class) {
				return new Long(Long.parseLong(s));
			}
			if (type == short.class) {
				return new Short(Short.parseShort(s));
			}
		}
		return value;
	}

}

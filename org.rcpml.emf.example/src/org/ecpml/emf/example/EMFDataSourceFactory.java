package org.ecpml.emf.example;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.rcpml.core.datasource.DataSourceUtils;
import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.datasource.IDataSourceElementBinding;
import org.rcpml.core.datasource.IDataSourceFactory;
import org.w3c.dom.Node;

/**
 * @author Yuri Strot
 *
 */
public class EMFDataSourceFactory implements IDataSourceFactory {
	
	public static class EMFDataSource implements IDataSource {
		
		public EMFDataSource( ) {
		}
		
		public void bind(IDataSourceElementBinding object, String path) {
			try {
				EMFLoader loader = EMFLoader.getCurrent();
				if (loader != null) {
					int index = path.indexOf(':');
					if (index >= 0 && index + 1 < path.length()) {
						path = path.substring(index + 1);
					}
					EObject obj = loader.getResource().getEObject(path);
					if (obj != null) {
						Iterator attrs = obj.eClass().getEAllStructuralFeatures().iterator();
						EStructuralFeature feature = (EStructuralFeature)attrs.next();
						if (feature != null) {
							DataSourceUtils.bindDataSourceElement(object, 
									new EMFDataSourceElementBinding(obj, feature));
						}
					}
				}
			}
			catch (Exception e) {
				//do not allow exception there
			}
		}
		
		public String getName() {
			return "org.rcpml.emf.datasource";
		}		
	}
	
	public IDataSource createDataSource(Node node) {		
		return new EMFDataSource(); 
	}	
}

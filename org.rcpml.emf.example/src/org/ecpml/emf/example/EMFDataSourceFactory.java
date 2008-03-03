package org.ecpml.emf.example;

import org.rcpml.core.IController;
import org.rcpml.core.datasource.IDataSource;
import org.rcpml.core.datasource.IDataSourceElementBinding;
import org.rcpml.core.datasource.IDataSourceFactory;
import org.w3c.dom.Node;

import com.xored.scripting.core.ScriptException;

/**
 * @author Yuri Strot
 *
 */
public class EMFDataSourceFactory implements IDataSourceFactory {
	
	public static class EMFDataSource implements IDataSource {
		
		private EMFLoader loader;
		
		public EMFDataSource(EMFLoader loader) {
			this.loader = loader;
		}
		
		public void bind(IDataSourceElementBinding object, String path) {
			try {
				if (loader != null) {
					int index = path.indexOf(':');
					if (index >= 0 && index + 1 < path.length()) {
						path = path.substring(index + 1);
					}
					index = path.lastIndexOf("/");
					if (index >= 0) {
						String featureName = path.substring(index + 1);
						String objectURI = path = path.substring(0, index);
						loader.addBinding(objectURI, featureName, object);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				//do not allow exception there
			}
		}
		
		public String getName() {
			return "org.rcpml.emf.datasource";
		}		
	}
	
	public IDataSource createDataSource(IController controller, Node node) {
		EMFLoader loader = new EMFLoader(controller);
		try {
			loader.readObject(controller.getScriptManager(
					).getDefaultContext().getBoundObject("editorInput"));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return new EMFDataSource(loader); 
	}	
}

package org.ecpml.emf.example;

import org.eclipse.ui.IFileEditorInput;
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
	
	private static final String INPUT_VARIABLE = "editorInput";
	
	public static class EMFDataSource implements IDataSource {
		
		private EMFBindingManager loader;
		
		public EMFDataSource(EMFBindingManager loader) {
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
				//do not allow exception there
			}
		}
		
		public String getName() {
			return "org.rcpml.emf.datasource";
		}		
	}
	
	public IDataSource createDataSource(IController controller, Node node) {
		EMFBindingManager loader = new EMFBindingManager(controller);
		IFileEditorInput input = getInput(controller);
		if (input != null)
			loader.setInput(input);
		return new EMFDataSource(loader); 
	}
	
	private IFileEditorInput getInput(IController controller) {
		try {
			return (IFileEditorInput)controller.getScriptManager().getDefaultContext(
				).getBoundObject(INPUT_VARIABLE);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
}

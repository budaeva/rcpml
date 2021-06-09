package org.ecpml.emf.example;

import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.w3c.dom.Node;

import com.xored.scripting.core.ScriptException;


/**
 * @author Yuri Strot
 *
 */
public class JLoaderTagFactory extends AbstractBridgeFactory {

	private static class JLoaderBridge extends AbstractBridge {
		
		protected JLoaderBridge(Node node, IController container) {
			super(node, container);
			init(container);
		}
		
		public void init(IController container) {
				try {
					getController().getScriptManager().getDefaultContext(
							).bindObject("emfLoader", new EMFBindingManager(container));
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

		public Object getPresentation() {
			return null;
		}
		
	}

	public JLoaderTagFactory() {
	}

	public IBridge createBridge(Node node) throws RCPMLException {
		return new JLoaderBridge(node, getController());
	}
}

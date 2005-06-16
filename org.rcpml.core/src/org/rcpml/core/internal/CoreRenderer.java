package org.rcpml.core.internal;

import org.rcpml.core.AbstractRenderer;
import org.rcpml.core.IController;
import org.rcpml.core.IScriptingContext;
import org.rcpml.core.IScriptingLanguage;
import org.rcpml.core.RCPMLException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CoreRenderer extends AbstractRenderer {
	
	private final static String TAG_SCRIPT = "script";

	private final static String ATTR_LANGUAGE = "language";
	
	private final static String DEFAULT_LANGAUGE = "javascript";
	
	private IScriptingContext context;
		
	CoreRenderer(IController controller) {
		super(controller);
	}
	
	private void createScriptingContext(String langName) {
		IScriptingLanguage language = 
			ScriptManager.getScriptingLanguage(langName);
		if(language == null) {
			throw new RCPMLException("unsupported language: " + langName);
		}
		context = language.createContext();		
	}
	
	IScriptingContext getScriptingContext() {
		if(context == null) {
			createScriptingContext(DEFAULT_LANGAUGE);
		}
		return context;
	}
		
	public Object renderNode(Node node, Object target) {
		String name = node.getLocalName();
		if(TAG_SCRIPT.equals(name)) {
			String langName = getAttribute(node, ATTR_LANGUAGE);
			if(langName == null) 
				langName = DEFAULT_LANGAUGE;
			if(context == null) {
				createScriptingContext(langName);
			} else {
				if(!langName.equals(context.getLanguageName())) 					
					throw new RCPMLException("incompatible languages");
			}
			String script = getChildrenAsText((Element)node);
			context.executeScript(script);
		}
		return null;
	}

}

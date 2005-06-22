package org.rcpml.core.internal;

import org.rcpml.core.AbstractRenderer;
import org.rcpml.core.IController;
import org.rcpml.core.IScriptingContext;
import org.rcpml.core.IScriptingLanguage;
import org.rcpml.core.RCPMLException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;

public class CoreRenderer extends AbstractRenderer implements IScriptingContext {
	
	private final static String TAG_SCRIPT = "script";

	private final static String ATTR_LANGUAGE = "language";
	
	private final static String DEFAULT_LANGUAGE = "javascript";
	
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
		Controller controller = (Controller) getController();
		context = language.createContext(controller.getBundle());		
	}
	
	private void checkContext() {
		if(context == null) {
			createScriptingContext(DEFAULT_LANGUAGE);
		}
	}
			
	public Object executeScript(String script) {
		checkContext();
		return context.executeScript(script);
	}

	public String getLanguageName() {
		checkContext();
		return context.getLanguageName();
	}

	public Object renderNode(Node node, Object target) {
		String name = node.getLocalName();
		if(TAG_SCRIPT.equals(name)) {
			String langName = getAttribute(node, ATTR_LANGUAGE);
			if(langName == null) 
				langName = DEFAULT_LANGUAGE;
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

	public void setEvent(Event event) {
		checkContext();
		context.setEvent(event);
	}

}

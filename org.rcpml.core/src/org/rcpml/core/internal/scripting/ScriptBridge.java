package org.rcpml.core.internal.scripting;

import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.scripting.IScriptContextManager;
import org.rcpml.core.scripting.IScriptingContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;

public class ScriptBridge extends AbstractBridge implements IScriptingContext
{
	private final static String ATTR_LANGUAGE = "language";

	private IScriptingContext context;	

	public ScriptBridge( Node node, IController container )
	{		
		super( node, container, false );
		System.out.println("Script constructed");
		
		String langName = DOMUtils.getAttribute(node, ATTR_LANGUAGE);
		if (langName == null) {
			langName = ScriptContextManager.DEFAULT_LANGUAGE;
		}
		IScriptContextManager manager = this.getController().getScriptManager();
		context = manager.getContext(langName);
		
		String script = DOMUtils.getChildrenAsText((Element) node);
		context.executeScript(script);
	}	

	public Object executeScript(String script)
	{
		return context.executeScript(script);
	}

	public String getLanguageName()
	{
		return context.getLanguageName();
	}

	public void setEvent(Event event)
	{
		context.setEvent(event);
	}

	public Object getPresentation() {
		return null;
	}

	public void bindObject(String name, Object object) {
		context.bindObject(name, object);
	}
}

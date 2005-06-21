package org.rcpml.core;

import org.w3c.dom.events.Event;

public interface IScriptingContext {

	Object executeScript(String script);
	
	/**
	 * Set event object.
	 * 
	 * @param event
	 * @return
	 */
	void setEvent(Event event);
	
	String getLanguageName();
}

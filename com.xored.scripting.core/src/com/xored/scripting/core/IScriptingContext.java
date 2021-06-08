package com.xored.scripting.core;

import org.w3c.dom.events.Event;

public interface IScriptingContext {

	Object executeScript(String script);
	
	/**
	 * Set event object.	 
	 */
	void setEvent(Event event);
	
	/**
	 * Return script language.
	 */
	String getLanguageName();
	
	/**
	 * Bind object into script scope.
	 */
	void bindObject( String name, Object object );
}
package com.xored.scripting.core;

public interface IScriptContextManager {	
	/**
	 * Return context, or null if no property are defined.
	 */
	IScriptingContext getContext( String language );	
	
	IScriptingContext getDefaultContext( );
	
	/**
	 * script is script code string. it can start from "languagename:" then 
	 * used selected language. Instead default language are used.
	 */
	Object executeScript(String script);
	
	/**
	 * Set selected language as default.
	 */
	void setDefaultLanguage( String language );
	
	/**
	 * Return default language. 
	 */
	String getDefaultLanguage();

	/**
	 * Used to determine required scripting context from script.
	 */
	IScriptingContext getContextFrom(String script);
}
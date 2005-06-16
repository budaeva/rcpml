package org.rcpml.core;

public interface IScriptingContext {

	Object executeScript(String script);
	
	String getLanguageName();
}

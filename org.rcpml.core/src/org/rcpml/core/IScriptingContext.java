package org.rcpml.core;

public interface IScriptingContext {

	void executeScript(String script);
	
	String getLanguageName();
}

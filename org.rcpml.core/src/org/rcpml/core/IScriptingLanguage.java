package org.rcpml.core;

public interface IScriptingLanguage {

	IScriptingContext createContext(ClassLoader classLoader);
}

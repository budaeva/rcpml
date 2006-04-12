package org.rcpml.core.scripting;

import org.osgi.framework.Bundle;

public interface IScriptingLanguage {

	IScriptingContext createContext( Bundle provider);
}

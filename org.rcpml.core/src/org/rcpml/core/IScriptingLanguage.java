package org.rcpml.core;

import org.osgi.framework.Bundle;

public interface IScriptingLanguage {

	IScriptingContext createContext(Bundle provider);
}

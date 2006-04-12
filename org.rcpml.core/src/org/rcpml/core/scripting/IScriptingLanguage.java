package org.rcpml.core.scripting;

import org.osgi.framework.Bundle;
import org.w3c.dom.Document;

public interface IScriptingLanguage {

	IScriptingContext createContext( Bundle provider);
}

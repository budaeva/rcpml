package com.xored.scripting.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
//import org.rcpml.core.RCPMLException;
//import com.xored.scripting.core.IScriptingLanguage;

public class ScriptManager {

	private final static String SCRIPT_EXT_POINT = "org.rcpml.core.language";

	private final static String LANGUAGE_ATTR = "languageName";

	private final static String CLASS_ATTR = "class";

	private static Map/*<String, Object>*/ scriptingLanguages;

	private static void loadLanguages() {
		scriptingLanguages = new HashMap/*<String, Object>*/();
		IConfigurationElement[] conf = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(SCRIPT_EXT_POINT);
		for (int i = 0; i < conf.length; i++) {
			String lang = conf[i].getAttribute(LANGUAGE_ATTR);
			scriptingLanguages.put(lang, conf[i]);
		}
	}

	static IScriptingLanguage getScriptingLanguage(String language) {
		if (scriptingLanguages == null)
			loadLanguages();
		Object lang = scriptingLanguages.get(language);
		if (lang == null)
			throw new RuntimeException("no language: " + language);
		if (!(lang instanceof IScriptingLanguage)) {
			try {
				lang = ((IConfigurationElement) lang)
						.createExecutableExtension(CLASS_ATTR);
			} catch (CoreException e) {
				// TODO report error
				e.printStackTrace();
				return null;
			}
			scriptingLanguages.put(language, lang);
		}
		return (IScriptingLanguage) lang;
	}
}
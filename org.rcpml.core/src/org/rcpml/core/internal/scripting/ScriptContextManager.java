package org.rcpml.core.internal.scripting;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.rcpml.core.RCPML;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.scripting.IScriptContextManager;
import org.rcpml.core.scripting.IScriptingContext;
import org.rcpml.core.scripting.IScriptingLanguage;
import org.w3c.dom.Document;

public class ScriptContextManager implements IScriptContextManager {
	
	public final static String DEFAULT_LANGUAGE = "javascript";	
	
	private Map<String, IScriptingContext> fScriptingContexts = new HashMap<String, IScriptingContext>();
	
	private String fDefaultLanguage = DEFAULT_LANGUAGE;
	
	private Document fDocument;
	
	private Bundle fBundle;
	
	public ScriptContextManager( Document document, Bundle bundle ) {
		this.fDocument = document;
		this.fBundle = bundle;
	}

	public IScriptingContext getContext(String language) throws RCPMLException {
		
		if( language == null ) {
			language = fDefaultLanguage;
		}
		
		// already created context.
		if( this.fScriptingContexts.containsKey(language) ) {
			return this.fScriptingContexts.get(language);
		}
		
		IScriptingLanguage scriptingLanguage = ScriptManager.getScriptingLanguage( language );
		//if (language == null) {
//			throw new RCPMLException("unsupported language: " + language );
	//	}
		IScriptingContext context = scriptingLanguage.createContext( this.fBundle );
		
		this.bindDefaultObjects( context );
		
		this.fScriptingContexts.put( language, context );
		return context;
	}	

	private void bindDefaultObjects(IScriptingContext context) {
		context.bindObject( "document", fDocument );		
	}

	public Object executeScript(String script) {
		//TODO: Possible add other language scripting.
		IScriptingContext context = this.getContext( DEFAULT_LANGUAGE );
		return context.executeScript(script);
	}

	public void setDefaultLanguage(String language) {		
		this.fDefaultLanguage = language;
	}

	public String getDefaultLanguage() {
		return this.fDefaultLanguage;
	}

}

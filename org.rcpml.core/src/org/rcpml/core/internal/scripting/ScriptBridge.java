package org.rcpml.core.internal.scripting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.dom.DOMUtils;
import org.rcpml.core.internal.contentprovider.ContentProviderManager;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.xored.scripting.core.IScriptContextManager;
import com.xored.scripting.core.IScriptingContext;
import com.xored.scripting.core.ScriptException;

public class ScriptBridge extends AbstractBridge implements IScriptingContext
{
	private final static String ATTR_LANGUAGE = "language";

	private static final String SRC = "src";

	private IScriptingContext context;	

	public ScriptBridge( Node node, IController container )
	{		
		super( node, container, false );
		//System.out.println("Script constructed");
		
		String langName = DOMUtils.getAttribute(node, ATTR_LANGUAGE);
		if (langName == null) {
			langName = "javascript";
		}
		IScriptContextManager manager = this.getController().getScriptManager();
		try {
			context = manager.getContext(langName);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		String script = DOMUtils.getChildrenAsText((Element) node);
		
		if( useExternalScript()) {
			script = getExternalScript();
		}
				
		context.executeScript(script);
	}	

	private String getExternalScript() {
		String scriptSource = getAttribute(SRC);
		if( scriptSource != null && scriptSource.length() > 0 ) {
			ContentProviderManager provider = ContentProviderManager.getInstance();
			String content = "";
			try {
				InputStream is = provider.getStream(scriptSource);
				if( is != null ) {
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					try {
						String line = br.readLine();
						while( line != null ) {
							content += line + "\n";
							line = br.readLine();
						}
					}
					catch( IOException ioex ) {
						ioex.printStackTrace();
					}
				}
			}
			catch( CoreException ex ) {
				ex.printStackTrace();
			}
			return content;
		}
		return null;
	}

	private boolean useExternalScript() {
		String scriptSource = getAttribute(SRC);
		if( scriptSource != null && scriptSource.length() > 0 ) {
			return true;
		}
		return false;
	}

	public Object executeScript(String script)
	{
		return context.executeScript(script);
	}

	public String getLanguageName()
	{
		return context.getLanguageName();
	}

	public Object getPresentation() {
		return null;
	}

	public void bindObject(String name, Object object) {
		context.bindObject(name, object);
	}

	public boolean supportExecution(URL url) {
		// TODO Auto-generated method stub
		return false;
	}

	public void unBindObject(String name) {
		// TODO Auto-generated method stub
		
	}

	public Object executeScript(Reader reader, String file) {
		throw new RuntimeException("not implemented");
	}
}

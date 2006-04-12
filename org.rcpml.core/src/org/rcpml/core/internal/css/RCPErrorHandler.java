package org.rcpml.core.internal.css;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class RCPErrorHandler implements ErrorHandler {

	public void warning(CSSParseException ex) throws CSSException {		
		System.out.println("RCPError:" + ex.getMessage());
	}

	public void error(CSSParseException ex) throws CSSException {
		System.out.println("RCPError:" + ex.getMessage());
	}

	public void fatalError(CSSParseException ex) throws CSSException {
		System.out.println("RCPError:" + ex.getMessage());
	}

}

package org.rcpml.core.internal.bridge;

import org.rcpml.core.internal.IRCPMLConstants;

public abstract class AbstractRCPBridge implements IBridge, IRCPMLConstants {

	protected AbstractRCPBridge() {
	}

    /**
     * Returns the RCPML namespace URI.
     */
    public String getNamespaceURI() {
        return RCPML_NAMESPACE_URI;
    }

    /**
     * Returns a new instance of this bridge.
     */
    public IBridge getInstance() {
        return this;
    }
	
}

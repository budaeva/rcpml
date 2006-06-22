package org.rcpml.swt;

import org.eclipse.swt.widgets.Composite;
import org.rcpml.core.IRCPMLConstructor;

public interface ICompositeParentConstructor extends IRCPMLConstructor {	
	Object createInstance( Composite parent );
}

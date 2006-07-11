package org.rcpml.tools.internal.namespaceDefinition;

import java.util.ArrayList;
import java.util.List;

import org.rcpml.tools.namespaceDefinition.INamespaceTagDefinition;

public class NamespaceTagDefinition implements INamespaceTagDefinition {
	private List fAttributes = new ArrayList();
	private String fName;
	private String fDefinition;
	
	public NamespaceTagDefinition( String name, String definition ) {
		this.fName = name;
		this.fDefinition = definition;
	}
	public List getAttributes() {
		return this.fAttributes;
	}

	public String getDefinition() {
		return this.fDefinition;
	}

	public String getName() {
		return this.fName;
	}
}

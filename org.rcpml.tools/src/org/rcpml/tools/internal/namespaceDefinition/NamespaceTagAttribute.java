package org.rcpml.tools.internal.namespaceDefinition;

import org.rcpml.tools.namespaceDefinition.INamespaceTagAttribute;

public class NamespaceTagAttribute implements INamespaceTagAttribute {
	private String fName;
	private boolean fRequired;
	
	public NamespaceTagAttribute( String name, boolean required ) {
		this.fName = name;
		this.fRequired = required;
	}
	public String getName() {
		return this.fName;
	}

	public boolean isRequired() {
		return this.fRequired;
	}
}

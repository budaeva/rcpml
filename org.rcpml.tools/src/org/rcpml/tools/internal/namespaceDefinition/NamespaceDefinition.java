package org.rcpml.tools.internal.namespaceDefinition;

import java.util.ArrayList;
import java.util.List;

import org.rcpml.tools.namespaceDefinition.INamespaceDefinition;

public class NamespaceDefinition implements INamespaceDefinition  {
	private String fName;
	private List fTags = new ArrayList();
	
	public NamespaceDefinition( String name ) {
		this.fName = name;
	}
	public String getName() {
		return this.fName;
	}

	public List getTags() {
		return this.fTags;
	}

}

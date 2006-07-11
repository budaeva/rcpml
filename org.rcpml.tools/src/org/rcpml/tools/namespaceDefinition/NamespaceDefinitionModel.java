package org.rcpml.tools.namespaceDefinition;

import java.util.List;

import org.rcpml.tools.internal.namespaceDefinition.NamespaceDefinitionManager;

public class NamespaceDefinitionModel {
	public static List getNamespaces() {
		return NamespaceDefinitionManager.getNamespaces();
	}
}

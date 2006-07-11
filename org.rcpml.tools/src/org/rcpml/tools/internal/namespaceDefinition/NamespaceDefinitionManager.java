package org.rcpml.tools.internal.namespaceDefinition;

import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class NamespaceDefinitionManager {
	private static final String NAMESPACE = "namespace";

	private final static String BRIDGEBUILDER_EXT_POINT = "org.rcpml.core.namespace";

	private final static String XMLNS_ATTR = "xmlNS";	

	public static List getNamespaces() {

		IConfigurationElement[] confs = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(BRIDGEBUILDER_EXT_POINT);
		if( confs != null ) {
			for (int ci = 0; ci < confs.length; ++ci) {
				IConfigurationElement configElement = confs[ci];
				String xmlNS = configElement.getAttribute(XMLNS_ATTR);
				NamespaceDefinition nsDef = new NamespaceDefinition(xmlNS);
				IConfigurationElement tags[] = configElement.getChildren("tag");
				if( tags != null ) {
					for( int j = 0; j < tags.length; ++j ) {
						IConfigurationElement tag = tags[j];
						String name = tag.getAttribute("name"); 
						String definition = "";
						IConfigurationElement def[] = tag.getChildren("definition");
						if( def != null && def.length == 1) {
							definition = def[0].getValue();
						}
						
						NamespaceTagDefinition tagDef = new NamespaceTagDefinition(name, definition);
						
						IConfigurationElement attributes[] = tag.getChildren("attribute");
					}
				}
			}
		}
		return null;
	}
}

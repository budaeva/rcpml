package org.rcpml.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridgeFactory;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.bridge.IBridgeFactory;
import org.w3c.dom.Node;

public final class BridgeFactoryManager extends AbstractBridgeFactory {

	private final static String BRIDGEBUILDER_EXT_POINT = "org.rcpml.core.namespace";

	private final static String XMLNS_ATTR = "xmlNS";

	private final static String CLASS_ATTR = "class";

	private final static String TAG_CHILD_NAME = "tag";

	private final static String NAME_ATTR = "name";

	private List<IConfigurationElement> fConfigurations = new ArrayList<IConfigurationElement>();

	private Map<String, IBridgeFactory> fLoadedPlugins = new HashMap<String, IBridgeFactory>();	

	public BridgeFactoryManager() {
		this.loadBuilders();
	}

	private void loadBuilders() {
		IConfigurationElement[] confs = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(BRIDGEBUILDER_EXT_POINT);

		for (IConfigurationElement configElement : confs) {

			String ns = configElement.getAttribute(XMLNS_ATTR);
			IConfigurationElement[] tagConfigs = configElement
					.getChildren(TAG_CHILD_NAME);

			for (IConfigurationElement config : tagConfigs) {
				String tag = config.getAttribute(NAME_ATTR);
				System.out
						.println("rcpml: Found tag builder:" + ns + ":" + tag);
			}
			this.fConfigurations.add(configElement);
		}
	}	

	private IBridgeFactory getBridgeBuilder(String namespace, String tagName) {

		String nsTag = this.makeNSTag(namespace, tagName);
		if (this.fLoadedPlugins.containsKey(nsTag)) {
			return this.fLoadedPlugins.get(nsTag);
		}
		return this.loadBuilder(namespace, tagName);
	}

	private String makeNSTag(String namespace, String tagName) {
		return namespace + ":" + tagName;
	}

	private IBridgeFactory loadBuilder(String namespace, String tagName) {
		for (IConfigurationElement configElement : this.fConfigurations) {

			String xmlNS = configElement.getAttribute(XMLNS_ATTR);
			if (xmlNS.equals(namespace)) {
				IConfigurationElement[] tags = configElement
						.getChildren(TAG_CHILD_NAME);
				for (IConfigurationElement config : tags) {
					String tag = config.getAttribute(NAME_ATTR);
					if (tag.equals(tagName)) {
						try {
							Object ee = config
									.createExecutableExtension(CLASS_ATTR);
							if (ee instanceof IBridgeFactory) {
								IBridgeFactory builder = (IBridgeFactory) ee;
								builder.setController(this.getController());
								this.fLoadedPlugins.put(this.makeNSTag(xmlNS,
										tag), builder);
								return builder;
							} else {
								throw new RCPMLException(
										"BridgeBuilder interface mismatch");
							}
						} catch (CoreException coreEx) {
							coreEx.printStackTrace();
						}
					}
				}
			}
		}
		throw new RCPMLException("ExtensionBridgeBuilder: unknown builder for:"
				+ namespace + ":" + tagName);
	}	

	public IBridge createBridge(Node node) {
		String namespace = node.getNamespaceURI();
		String tagName = node.getLocalName();// node.getLocalName();
		if (tagName == null) {
			tagName = node.getNodeName();
		}

		IBridgeFactory bridgeBuilder = this
				.getBridgeBuilder(namespace, tagName);
		if (bridgeBuilder != null) {
			IBridge bridge =  bridgeBuilder.createBridge(node);
			if( bridge == null ) {
				throw new RCPMLException("Created null bridge for" + namespace + ":" + tagName );
			}
			return bridge;
		}
		return null;
	}	
}

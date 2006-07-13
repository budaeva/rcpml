package org.rcpml.core.internal.datasource;

import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.datasource.IDataSource;
import org.w3c.dom.Node;

public class DataSourceBridge extends AbstractBridge implements IBridge {
	private static final String NAME_ATTR = "name";

	private static final String SRC_ATTR = "src";

	private IDataSource fDataSource;

	protected DataSourceBridge(Node node, IController controller) {
		super(node, controller, false);

		String src = getAttribute(SRC_ATTR);
		if (src.length() > 0) {
			this.fDataSource = DataSourceManager.getInstance().getDataSource(
					src, node );
		} else {
			System.err.println("Warning: DataSource: required src element");
			this.fDataSource = DataSourceManager.getInstance()
					.getLocalDataSource(node);
		}
	}

	public Object getPresentation() {
		return fDataSource;
	}

	public String getName() {		
		return getAttribute(NAME_ATTR);
	}
}

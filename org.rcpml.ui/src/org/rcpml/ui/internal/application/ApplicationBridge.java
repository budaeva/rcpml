package org.rcpml.ui.internal.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
//import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.rcpml.core.IController;
import org.rcpml.core.bridge.AbstractBridge;
import org.w3c.dom.Node;

public class ApplicationBridge extends AbstractBridge {
	private static final String PERSPECTIVE_ATTR = "perspective";

	private static final String TITLE_ATTR = "title";

	// move to styles
	private static final String WIDTH_ATTR = "width";

	private static final String HEIGHT_ATTR = "height";

	private ApplicationRunnable fRunnable;

	private String fPerspective;

	private String fTitle;

	private int fWidth;

	private int fHeight;

	protected ApplicationBridge(Node node, IController controller) {
		super(node, controller, true);

		fPerspective = this.getAttribute(PERSPECTIVE_ATTR);
		fTitle = this.getAttribute(TITLE_ATTR);

		fWidth = Integer.parseInt(this.getAttribute(WIDTH_ATTR));
		fHeight = Integer.parseInt(this.getAttribute(HEIGHT_ATTR));
	}

	public Object getPresentation() {
		if (fRunnable == null) {
			this.fRunnable = new ApplicationRunnable();
		}
		return fRunnable;
	}

	private class ApplicationRunnable implements IApplication {

		@Override
		public Object start(IApplicationContext context) throws Exception {
			Display display = PlatformUI.createDisplay();
			try {

				int returnCode = PlatformUI.createAndRunWorkbench(display,
						new ApplicationWorkbenchAdvisor());
				if (returnCode == PlatformUI.RETURN_RESTART) {
					return IApplication.EXIT_RESTART;
				}
				getController().bridgeDisposed(ApplicationBridge.this);
				return IApplication.EXIT_OK;
			} finally {
				
				display.dispose();
			}
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}
	}

	private class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
		public String getInitialWindowPerspectiveId() {
			return fPerspective;
		}

		public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
				IWorkbenchWindowConfigurer configurer) {
			return new ApplicationWorkbenchWindowAdvisor(configurer);
		}
	}

	private class ApplicationWorkbenchWindowAdvisor extends
			WorkbenchWindowAdvisor {

		public ApplicationWorkbenchWindowAdvisor(
				IWorkbenchWindowConfigurer configurer) {
			super(configurer);
		}

		public ActionBarAdvisor createActionBarAdvisor(
				IActionBarConfigurer configurer) {
			return new ApplicationActionBarAdvisor(configurer);
		}

		public void preWindowOpen() {
			IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
			configurer.setInitialSize(new Point(fWidth, fHeight));
			configurer.setShowCoolBar(false);
			configurer.setShowStatusLine(false);
			configurer.setTitle(fTitle);
		}
	}

	public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

		private IWorkbenchWindow fWindow;

		public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
			super(configurer);
			this.fWindow = configurer.getWindowConfigurer().getWindow();
		}

		protected void makeActions(final IWorkbenchWindow window) {
		}

		protected void fillMenuBar(IMenuManager menuBar) {
		}

		protected IWorkbenchWindow getWindow() {
			return this.fWindow;
		}
	}
}

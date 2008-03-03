package org.rcpml.emf.example.synch;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.ui.IFileEditorInput;

/**
 * @author Yuri Strot
 *
 */
public abstract class EMFSynchronizator implements IFileChangeListener {
	
	private Resource resource;
	private IFile file;
	
	private AdapterFactoryEditingDomain getEditDomain() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		
		BasicCommandStack commandStack = new BasicCommandStack();
		
		return new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap());
	}
	
	protected void updateFile(IFileEditorInput modelFile) {
		
		file = modelFile.getFile();
		
		FileListener.getInstance().removeListener(this);
		FileListener.getInstance().addListener(this);
		
		updateResource();
	}
	
	protected void updateResource() {
		AdapterFactoryEditingDomain editingDomain = getEditDomain();
		
		URI resourceURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		Resource resource = null;
		try {
			resource = editingDomain.getResourceSet().getResource(resourceURI, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			resource = editingDomain.getResourceSet().getResource(resourceURI, false);
		}
		this.resource = resource;
	}
	
	protected Resource getResource() {
		return resource;
	}
	
	public void setInput(IFileEditorInput input) {
		updateFile(input);
		Iterator it = resource.getContents().iterator();
		while (it.hasNext()) {
			EObject object = (EObject) it.next();
			System.out.println(resource.getURIFragment(object));
		}
	}

	public void fileChanged() {
		updateResource();
	}

	public void fileRemoved() {
	}
	
	public File getFile() {
		return file.getLocation().toFile();
	}

}

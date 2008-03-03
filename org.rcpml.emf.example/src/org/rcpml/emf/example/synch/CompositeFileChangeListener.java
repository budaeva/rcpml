package org.rcpml.emf.example.synch;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yuri Strot
 *
 */
public class CompositeFileChangeListener implements IFileChangeListener {
	
	private List listeners;
	private File file;
	
	public CompositeFileChangeListener(IFileChangeListener listener) {
		listeners = new ArrayList();
		listeners.add(listener);
		file = listener.getFile();
	}
	
	public void addListener(IFileChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IFileChangeListener listener) {
		listeners.remove(listener);
	}

	public void fileChanged() {
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			IFileChangeListener listener = (IFileChangeListener) it.next();
			listener.fileChanged();
		}
	}

	public void fileRemoved() {
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			IFileChangeListener listener = (IFileChangeListener) it.next();
			listener.fileRemoved();
		}
	}
	
	public IFileChangeListener getListener() {
		return (IFileChangeListener)listeners.get(0);
	}

	public File getFile() {
		return file;
	}
	
	public int getSize() {
		return listeners.size();
	}

}

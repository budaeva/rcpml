package org.rcpml.emf.example.synch;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Yuri Strot
 *
 */
public class FileListener {
	
	private static FileListener instance;
	
	private static long SLEEP_TIME = 100;
	
	private class BackgroundListener extends Thread {
		
		private HashMap listeners = new HashMap();
		private HashMap stampts = new HashMap();
		
		public BackgroundListener() {
			
		}
		
		public void run() {
				while(true) {
					try {
						Thread.sleep(SLEEP_TIME);
						File[] files = getFiles();
						for (int i = 0; i < files.length; i++) {
							File file = files[i];
							if (!file.exists()) {
								fireFileModified(file, true);
							}
							else {
								Long stamp = new Long(file.lastModified());
								Long oldStamp = (Long)stampts.get(file);
								if (oldStamp == null) {
									stampts.put(file, stamp);
								}
								else if (!oldStamp.equals(stamp)) {
									stampts.put(file, stamp);
									fireFileModified(file, false);
								}
							}
						}
					}
					catch (Exception e) {
						//ignore exception
					}
			}
		}
		
		private File[] getFiles() {
			synchronized (listeners) {
				Set files = listeners.keySet();
				return (File[])files.toArray(new File[files.size()]);
			}
		}
		
		private void fireFileModified(File file, boolean removed) {
			synchronized (listeners) {
				IFileChangeListener listener = 
					(IFileChangeListener)listeners.get(file);
				if (listener != null) {
					if (removed) {
						listener.fileRemoved();
						listeners.remove(file);
					}
					else
						listener.fileChanged();
				}
			}
		}
		
		public void addListener(IFileChangeListener listener) {
			synchronized (listeners) {
				File file = listener.getFile();
				IFileChangeListener oldListener = 
					(IFileChangeListener)listeners.get(file);
				if (oldListener == null) {
					listeners.put(file, listener);
				}
				else {
					if (oldListener instanceof CompositeFileChangeListener) {
						CompositeFileChangeListener compositeListener = 
							(CompositeFileChangeListener)listener;
						compositeListener.addListener(listener);
					}
					else {
						CompositeFileChangeListener compositeListener = 
							new CompositeFileChangeListener(oldListener);
						compositeListener.addListener(listener);
						listeners.put(file, compositeListener);
					}
				}
			}
		}
		
		public void removeListener(IFileChangeListener listener) {
			synchronized (listeners) {
				File file = listener.getFile();
				IFileChangeListener oldListener = 
					(IFileChangeListener)listeners.get(file);
				if (oldListener instanceof CompositeFileChangeListener) {
					CompositeFileChangeListener compositeListener = 
						(CompositeFileChangeListener)listener;
					compositeListener.removeListener(listener);
					if (compositeListener.getSize() == 1) {
						listeners.put(file, compositeListener.getListener());
					}
				}
				else {
					listeners.remove(file);
				}
			}
		}
		
	}
	
	private BackgroundListener bListener;
	
	private FileListener() {
		bListener = new BackgroundListener();
		bListener.start();
	}
	
	public static FileListener getInstance() {
		if (instance == null)
			instance = new FileListener();
		return instance;
	}
	
	public void addListener(IFileChangeListener listener) {
		bListener.addListener(listener);
	}
	
	public void removeListener(IFileChangeListener listener) {
		bListener.removeListener(listener);
	}

}

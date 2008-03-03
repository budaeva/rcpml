package org.rcpml.emf.example.synch;

import java.io.File;

/**
 * @author Yuri Strot
 *
 */
public interface IFileChangeListener {
	
	public File getFile();
	
	public void fileChanged();
	
	public void fileRemoved();

}

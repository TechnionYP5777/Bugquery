package com.bugquery.fixer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;


/**
 * Singleton for managing our IMarker's
 * @author doron
 * @since 03-Apr-17
 */

public class MarkerManager {
	
	private static MarkerManager instance = new MarkerManager();
	List<IMarker> markers;
	
	private MarkerManager() {
		this.markers = new ArrayList<IMarker>();
	}
	
	public static MarkerManager getInstance(){
	      return instance;
   }
	
	/**
	 * convenient method for marking file associated with this builder
	 * @param file
	 * @param message
	 * @param lineNumber
	 * @param severity
	 * @return 
	 */
	public IMarker addMarker(IFile file, String message, int lineNumber, int severity) {
		try {
			IMarker marker = file.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			markers.add(marker);
			return marker;
		} catch (CoreException e) {
			return null;
		}
	}
	
	public void deleteAllMarkers() {
		for(IMarker m : markers) {
			try {
				m.delete();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
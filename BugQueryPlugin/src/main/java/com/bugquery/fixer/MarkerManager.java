package com.bugquery.fixer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
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
	
	public static MarkerManager instance(){
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
	public IMarker addMarker(IFile file, String message, int lineNumber) {
		try {
			IMarker marker = file.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
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
	
	public IMarker addMarker(IFile file, String trace) {
		return addMarker(file, "Fix: " + StackTrace.of(trace).getException(), StackTrace.of(trace).getLine());
	}
	
	public void deleteMarkerFrom(IResource i) {
		try {
			i.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteAllKnownMarkers() {
		for(IMarker m : markers) {
			try {
				m.delete();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
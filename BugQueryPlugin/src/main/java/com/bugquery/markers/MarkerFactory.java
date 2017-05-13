package com.bugquery.markers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.PlatformUI;

/**
 * Singleton for managing our IMarker's
 * 
 * @author Doron
 * @since 03-Apr-17
 */

public class MarkerFactory {

	public static final String MARKER = "com.bugquery.markers.tracemarker";
	private static MarkerFactory instance = new MarkerFactory();
	List<IMarker> markers;

	private MarkerFactory() {
		markers = new ArrayList<>();
	}

	public static MarkerFactory instance() {
		return instance;
	}

	/**
	 * Method for creating new marker of our new type
	 * 
	 * @param res
	 *            resource the marker will be on
	 * @param msg
	 *            message the marker will show
	 * @param line
	 *            line number in the resource
	 * @return the new marker
	 * @throws CoreException
	 */
	public static IMarker createMarker(IResource res, String msg, int line)
			throws CoreException {
		IMarker marker = null;
		marker = res.createMarker("com.bugquery.markers.tracemarker");
		marker.setAttribute(IMarker.MESSAGE, msg);
		marker.setAttribute("description", "this is one of my markers");
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		if (line == -1)
			line = 1;
		marker.setAttribute(IMarker.LINE_NUMBER, line);
		return marker;
	}

	/**
	 * convenient method for marking file associated with this builder
	 * 
	 * @param file
	 * @param message
	 * @param lineNumber
	 * @param severity
	 * @return
	 */
	public IMarker addMarker(final IResource res, final String msg, int line) {
		try {
			IMarker m = createMarker(res, msg, line);
			markers.add(m);
			return m;
		} catch (final CoreException e) {
			return null;
		}
	}

	public IMarker addMarker(final IResource res, final String trace) {
		return addMarker(res, "Fix: " + StackTrace.of(trace).getException(),
				StackTrace.of(trace).getLine());
	}

	public void deleteMarkerFrom(final IResource i) {
		try {
			i.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllKnownMarkers() {
		for (final IMarker m : markers)
			try {
				m.delete();
			} catch (final CoreException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @param res
	 * @return a list of a resources markers
	 */
	public static List<IMarker> findMarkers(IResource res) {
		try {
			return Arrays.asList(
					res.findMarkers(MARKER, true, IResource.DEPTH_ZERO));
		} catch (CoreException e) {
			return new ArrayList<IMarker>();
		}
	}

	/**
	 * @param res
	 * @return a list of markers that are linked to the resource or any sub
	 *         resource of the resource
	 */
	public static List<IMarker> findAllMarkers(IResource res) {
		try {
			return Arrays.asList(
					res.findMarkers(MARKER, true, IResource.DEPTH_INFINITE));
		} catch (CoreException e) {
			return new ArrayList<IMarker>();
		}
	}

	/**
	 * @return the selection of the package explorer
	 */
	public static TreeSelection getTreeSelection() {

		ISelection selection = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (selection instanceof TreeSelection) {
			return (TreeSelection) selection;
		}
		return null;
	}
}
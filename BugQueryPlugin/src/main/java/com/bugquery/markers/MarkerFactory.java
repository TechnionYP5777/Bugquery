package com.bugquery.markers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.*;

import com.bugquery.stacktrace.Extract;

/**
 * Singleton for managing our IMarker's
 * 
 * @author Doron
 * @since 03-Apr-17
 */

public class MarkerFactory {

	public static final String MARKER = "com.bugquery.markers.tracemarker"; // "com.bugquery.markers.tracemarker";
	public static final String ANNOTATION = "com.bugquery.markers.traceannotation";
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
	 * @param line
	 *            line number in the resource
	 * @param msg
	 *            message the marker will show
	 * @return the new marker
	 * @throws CoreException
	 */
	public static IMarker createMarker(IResource res, int line, String msg)
			throws CoreException {
		IMarker marker = null;
		if (line == -1)
			return marker;
		marker = res.createMarker(MARKER);
		marker.setAttribute(IMarker.MESSAGE, msg);
		marker.setAttribute("description", "this is one of my markers");
		marker.setAttribute(IMarker.LINE_NUMBER, line);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		return marker;
	}

	public static IMarker createMarker(IResource res, Position position,
			String msg) throws CoreException {
		IMarker marker = null;
		if (position.isDeleted)
			return null;
		marker = res.createMarker(MARKER);
		marker.setAttribute(IMarker.MESSAGE, msg);
		int start = position.getOffset();
		int end = position.getOffset() + position.getLength();
		marker.setAttribute(IMarker.CHAR_START, start);
		marker.setAttribute(IMarker.CHAR_END, end);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
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
	public IMarker addMarker(final IResource res, int line, final String msg) {
		try {
			IMarker m = createMarker(res, line, msg);
			markers.add(m);
			return m;
		} catch (final CoreException e) {
			return null;
		}
	}

	public void addMarkers(String trace) {
		Map<String, Integer> lines = Extract.lines(trace);
		String exception = StackTrace.of(trace).getException();
		for (String f : Extract.files(trace)) {
			final IFile file = ResourcesUtils.getFile("Test", "src", f);
			addMarker(file, lines.get(f), "This line causes " + exception);
		}
	}

	public IMarker addMarker(final IResource res, final String trace) {
		return addMarker(res, StackTrace.of(trace).getLine(),
				"Fix: " + StackTrace.of(trace).getException());
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
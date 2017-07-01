package com.bugquery.markers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.Preferences;

import com.bugquery.stacktrace.Extract;

/**
 * Singleton for managing our IMarker's
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
	 * @param r
	 *            resource the marker will be on
	 * @param line
	 *            line number in the resource
	 * @param msg
	 *            message the marker will show
	 * @return the new marker
	 * @throws CoreException
	 */
	public static IMarker createMarker(IResource r, int line, String msg)
			throws CoreException {
		IMarker marker = null;
		if (line == -1)
			return marker;
		marker = r.createMarker(MARKER);
		marker.setAttribute(IMarker.MESSAGE, msg);
		marker.setAttribute("description", "this is one of my markers");
		marker.setAttribute(IMarker.LINE_NUMBER, line);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		return marker;
	}

	public static IMarker createMarker(IResource r, Position p,
			String msg) throws CoreException {
		IMarker marker = null;
		if (p.isDeleted)
			return null;
		marker = r.createMarker(MARKER);
		marker.setAttribute(IMarker.MESSAGE, msg);
		int start = p.getOffset(), end = p.getOffset() + p.getLength();
		marker.setAttribute(IMarker.CHAR_START, start);
		marker.setAttribute(IMarker.CHAR_END, end);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		return marker;
	}

	/**
	 * convenient method for marking file associated with this builder
	 * 
	 * @param url
	 * @param file
	 * @param message
	 * @param lineNumber
	 * @param severity
	 * @return
	 */
	public IMarker addMarker(final IResource r, int line, final String msg,
			String url) {
		try {
			IMarker m = createMarker(r, line, msg);
			m.setAttribute("currentURL", url);
			markers.add(m);
			return m;
		} catch (final CoreException e) {
			return null;
		}
	}

	/**
	 * Get a trace and a url, set markers of this project to link this url
	 * @param trace
	 * @param url
	 */
	public void addMarkers(String trace, String url) {
		String exception = StackTrace.of(trace).getException(); 
		ArrayList<MarkerInformation> markerInfo = Extract.markersInfo(trace);
		Preferences prefs = InstanceScope.INSTANCE
				.getNode("com.bugquery.preferences");
		String projectName = prefs.get("projectname", "default"), src = "src/";
		IJavaProject p = JavaCore
				.create(ResourcesUtils.getProject(projectName));
		try {
			IPackageFragmentRoot[] ipfr = p.getAllPackageFragmentRoots();
			for (IPackageFragmentRoot i : ipfr)
				if (i.getKind() == IPackageFragmentRoot.K_SOURCE) {
					src = i.getPath() + "";
					src = src.substring(src.indexOf("/") + 1);
					src = src.substring(src.indexOf("/") + 1);
					break;
				}
		} catch (JavaModelException e) {

		}
		for (MarkerInformation t : markerInfo) {
			String folder = src + "/" + t.getPackageName();
			final IFile file = ResourcesUtils.getFile(projectName, folder,
					t.getFileName());
			addMarker(file, t.getLineNumber(), "This line causes " + exception,
					url);
		}
	}

	/**
	 * Delete marker from IResource...
	 * @param r 
	 */
	public void deleteMarkerFrom(final IResource r) {
		try {
			r.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete all markers from all files and project
	 */
	public void deleteAllKnownMarkers() {
		for (final IMarker m : markers)
			try {
				m.delete();
			} catch (final CoreException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @param r
	 * @return a list of a resources markers
	 */
	public static List<IMarker> findMarkers(IResource r) {
		try {
			return Arrays.asList(
					r.findMarkers(MARKER, true, IResource.DEPTH_ZERO));
		} catch (CoreException e) {
			return new ArrayList<IMarker>();
		}
	}

	/**
	 * @param r
	 * @return a list of markers that are linked to the resource or any sub
	 *         resource of the resource
	 */
	public static List<IMarker> findAllMarkers(IResource r) {
		try {
			return Arrays.asList(
					r.findMarkers(MARKER, true, IResource.DEPTH_INFINITE));
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
		return !(selection instanceof TreeSelection) ? null : (TreeSelection) selection;
	}
}
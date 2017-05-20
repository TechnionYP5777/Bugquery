package com.bugquery.markers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.*;

/**
 * Singleton for managing our IMarker's
 * 
 * @author Doron
 * @since 03-Apr-17
 */

public class MarkerFactory {

	public static final String MARKER = "org.eclipse.core.resources.problemmarker"; //"com.bugquery.markers.tracemarker";
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

	public static IMarker createMarker(IResource res, Position position, String msg)
			throws CoreException {
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
	public IMarker addMarker(final IResource res, final String msg, int line) {
		try {
			IMarker m = createMarker(res, line, msg);
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

	public static void addAnnotation(IMarker marker, ITextSelection selection,
			ITextEditor editor) {
		// The DocumentProvider enables to get the document currently loaded in
		// the editor
		IDocumentProvider idp = editor.getDocumentProvider();

		// This is the document we want to connect to. This is taken from
		// the current editor input.
		IDocument document = idp.getDocument(editor.getEditorInput());

		// The IAnnotationModel enables to add/remove/change annotation to a
		// Document
		// loaded in an Editor
		IAnnotationModel iamf = idp.getAnnotationModel(editor.getEditorInput());

		// Note: The annotation type id specify that you want to create one of
		// your
		// annotations
		SimpleMarkerAnnotation ma = new SimpleMarkerAnnotation(ANNOTATION,
				marker);

		// Finally add the new annotation to the model
		iamf.connect(document);
		iamf.addAnnotation(ma,
				new Position(selection.getOffset(), selection.getLength()));
		iamf.disconnect(document);
	}
}
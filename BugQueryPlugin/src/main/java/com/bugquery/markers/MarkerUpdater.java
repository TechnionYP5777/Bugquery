package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.texteditor.IMarkerUpdater;

/**
 * Class for updating the marker if there was a change
 * 
 * @author Doron
 * @since 14 05 2017
 */
public class MarkerUpdater implements IMarkerUpdater {
	/**
	 * @return the attributes for which this updater is responsible. If the
	 *         result is null, the updater assumes responsibility for any
	 *         attributes.
	 */
	@Override
	public String[] getAttribute() {
		return null;
	}

	/**
	 * @return the marker type we are interested in updating
	 */
	@Override
	public String getMarkerType() {
		return MarkerFactory.MARKER;
	}

	/**
	 * @param marker
	 *            the marker to be updated
	 * @param doc
	 *            the document into which the given position points
	 * @param position
	 *            the current position of the marker inside the given document
	 * @return
	 */
	@Override
	public boolean updateMarker(IMarker marker, IDocument doc,
			Position position) {
		try {
			int start = position.getOffset();
			int end = position.getOffset() + position.getLength();
			marker.setAttribute(IMarker.CHAR_START, start);
			marker.setAttribute(IMarker.CHAR_END, end);
			return true;
		} catch (CoreException e) {
			return false;
		}
	}
}

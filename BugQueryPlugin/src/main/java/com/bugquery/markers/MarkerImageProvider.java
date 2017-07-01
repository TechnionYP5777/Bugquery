package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.internal.ide.IMarkerImageProvider;

/**
 * Class for when we wanted the markers to have a bugquery icon
 * @author Doron
 * @since 24 05 2017
 */

@SuppressWarnings("restriction")
public class MarkerImageProvider implements IMarkerImageProvider {

	@Override
	public String getImagePath(IMarker __) {
		return "icons/bugquery_console.gif";
	}

}

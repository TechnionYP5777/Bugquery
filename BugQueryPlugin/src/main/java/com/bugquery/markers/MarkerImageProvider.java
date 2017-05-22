package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.internal.ide.IMarkerImageProvider;

public class MarkerImageProvider implements IMarkerImageProvider {

	@Override
	public String getImagePath(IMarker marker) {
		return "icons/bugquery_console.gif";
	}
	
}

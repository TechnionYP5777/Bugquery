package com.bugquery.fixer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;

/**
 * This class offers a quick fix, attached to an {@link IMarker}. The fix cannot
 * remove he marker, but it opens new tab with browser on the query
 * 
 * @author doron
 * @since 03-Apr-17
 */

// TODO To be implemented --yg

public class QuickFixer implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker mk) {
		try {
			Object problem = mk.getAttribute("MESSAGE");
			return new IMarkerResolution[] { new QuickFix("Fix #1 for " + problem) };
		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}
	}

	private class QuickFix implements IMarkerResolution {
		String label;

		QuickFix(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public void run(IMarker marker) {
			// TODO implement this method.
			MessageDialog.openInformation(null, "QuickFix Demo", "This quick-fix is not yet implemented");
		}
	}

}

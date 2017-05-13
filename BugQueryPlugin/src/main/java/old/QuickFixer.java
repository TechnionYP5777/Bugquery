package old;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * ATTENTION: This class is on hold
 * 
 * This class offers a quick fix, attached to an {@link IMarker}. The fix cannot
 * remove the marker, but it opens new tab with browser on the query
 *
 * @author doron
 * @since 03-Apr-17
 */

// TODO To be implemented --yg

public class QuickFixer implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(final IMarker mk) {
		try {
			final Object problem = mk.getAttribute("MESSAGE");
			return new IMarkerResolution[] { new QuickFix("Fix #1 for " + problem) };
		} catch (final CoreException e) {
			return new IMarkerResolution[0];
		}
	}

	private class QuickFix implements IMarkerResolution {
		String label;

		QuickFix(final String label) {
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public void run(final IMarker marker) {
			MessageDialog.openInformation(null, "QuickFix Demo", "This quick-fix is not yet implemented");
		}
	}

}

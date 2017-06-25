package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.IMarkerResolution;
import org.osgi.service.prefs.Preferences;

import com.bugquery.actions.Dispatch;
import com.bugquery.actions.FromConsole;

/**
 * @author Doron
 * @since 25 06 2017
 */

public class TraceQuickFix implements IMarkerResolution {
    String label;
    TraceQuickFix(String label) {
       this.label = label;
    }
    public String getLabel() {
       return label;
    }
    public void run(IMarker marker) {
		Preferences prefs = InstanceScope.INSTANCE
				.getNode("com.bugquery.preferences");
		Program.launch(marker.getAttribute("currentURL", "http://ssdlbugquery.cs.technion.ac.il"));
    }
 }

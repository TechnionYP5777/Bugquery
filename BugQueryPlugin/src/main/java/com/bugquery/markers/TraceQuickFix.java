package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.IMarkerResolution;

/**
 * Single quickfix for our Trace Marker
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
    public void run(IMarker m) {
		InstanceScope.INSTANCE.getNode("com.bugquery.preferences");
		Program.launch(m.getAttribute("currentURL", "http://ssdlbugquery.cs.technion.ac.il"));
    }
 }

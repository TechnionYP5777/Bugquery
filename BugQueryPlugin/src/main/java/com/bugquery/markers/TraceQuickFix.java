package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IMarkerResolution;

public class TraceQuickFix implements IMarkerResolution {
    String label;
    TraceQuickFix(String label) {
       this.label = label;
    }
    public String getLabel() {
       return label;
    }
    public void run(IMarker marker) {
       MessageDialog.openInformation(null, "BugQuery QuickFix Demo",
          "Take me to church");
    }
 }

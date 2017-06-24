package com.bugquery.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class TraceQuickFixer implements IMarkerResolutionGenerator {
    public IMarkerResolution[] getResolutions(IMarker mk) {
	   return new IMarkerResolution[] {
         new TraceQuickFix("BugQuery has a solution for this problem... ")
      };
    }
 }
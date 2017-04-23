package com.bugquery.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

/** Specifies default attributes of our application, which hooks on eclipse's
 * launch extension point. The hook contributes to the "Run configuration...",  
 * and "Debug configuration..." of Eclipse. 
 * 
 * <p> All attributes are default in the  the current implementation, This
 * paragraph should be erased as the class evolves 
 * 
 * <p> Extended class {@link * JavaLaunchDelegate}, provides default values for
 * a run/debug on Java.
 * 
 * @author Yosef @since Jan 10 2017
 * 
 */
public final class QueryDelegate extends JavaLaunchDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		super.launch(configuration, mode, launch, monitor);
		/* Body is intentionally left as template, supply body as needed */
	}

}

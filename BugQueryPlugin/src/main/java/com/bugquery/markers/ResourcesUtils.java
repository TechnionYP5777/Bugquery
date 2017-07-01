package com.bugquery.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

/**
 * Helps with getting project objects
 * 
 * @author doron
 * @since 03-Apr-17
 */

public class ResourcesUtils {
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static IWorkspaceRoot getRoot() {
		return getWorkspace().getRoot();
	}

	/**
	 * @return list of opened projects
	 */
	public static List<IProject> getOpenedProjects() {
		List<IProject> $ = new ArrayList<IProject>();
		IWorkspaceRoot r = getRoot();
		for (IProject p : r.getProjects())
			if (p.isOpen())
				$.add(p);
		return $;
	}

	public static String getActiveProjectName() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null)
			return "";
		IStructuredSelection selection = (IStructuredSelection) window
				.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		if (!(firstElement instanceof IAdaptable))
			return "";
		IProject project = (IProject) ((IAdaptable) firstElement)
				.getAdapter(IProject.class);
		IPath path = project.getFullPath();
		return path + "";
	}

	public static IProject getProject(final String myProject) {
		return getRoot().getProject(myProject);
	}

	public static IFolder getFolder(final String myProject,
			final String myFolder) {
		return getProject(myProject).getFolder(myFolder);
	}

	public static IFile getFile(final String myProject, final String myFolder,
			final String myFile) {
		return getFolder(myProject, myFolder).getFile(myFile);
	}
}

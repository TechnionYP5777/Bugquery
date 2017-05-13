package com.bugquery.markers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

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

	public static IProject getProject(final String myProject) {
		return getRoot().getProject(myProject);
	}

	public static IFolder getFolder(final String myProject, final String myFolder) {
		return getProject(myProject).getFolder(myFolder);
	}

	public static IFile getFile(final String myProject, final String myFolder, final String myFile) {
		return getFolder(myProject, myFolder).getFile(myFile);
	}
}
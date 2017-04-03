package com.bugquery.fixer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * Helps with getting project objects
 * @author doron
 * @since 03-Apr-17
 */

public class ProjectUtils {
	public enum get {
		SINGLETON;

		public static IWorkspace workspace() {
			return ResourcesPlugin.getWorkspace();
		}

		public static IWorkspaceRoot root() {
			return get.workspace().getRoot();
		}

		public static IProject project(String myProject) {
			return get.root().getProject(myProject);
		}

		public static IFolder folder(String myProject, String myFolder) {
			return get.project(myProject).getFolder(myFolder);
		}

		public static IFile file(String myProject, String myFolder, String myFile) {
			return get.folder(myProject, myFolder).getFile(myFile);
		}
	}
}

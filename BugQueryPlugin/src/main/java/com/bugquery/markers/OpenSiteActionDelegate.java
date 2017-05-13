package com.bugquery.markers;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * Still not sure if this is the right implementation - class for opening the
 * site when the correct action is invoked
 * 
 * @author Doron
 * @since 14 05 2017
 */
public class OpenSiteActionDelegate implements IEditorActionDelegate {

	public OpenSiteActionDelegate() {
		super();
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart editor) {
		// TODO Auto-generated method stub
	}

	/**
	 * When editor action happen, this invokes
	 * 
	 * @param action
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}
}

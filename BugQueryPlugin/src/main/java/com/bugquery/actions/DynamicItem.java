package com.bugquery.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.actions.CompoundContributionItem;
import com.bugquery.markers.ResourcesUtils;

public class DynamicItem extends CompoundContributionItem {

	public DynamicItem() {
	}

	public DynamicItem(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		// Here you could get selection and decide what to do
		// You can also simply return if you do not want to show a menu

		// create the menu item
		for (IProject p : ResourcesUtils.getOpenedProjects()) {
			MenuItem menuItem = new MenuItem(menu, SWT.RADIO, index);
			menuItem.setText(p.getName());
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					System.out.println(p.getName());
				}
			});
		}
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		// TODO Auto-generated method stub
		return null;
	}

}
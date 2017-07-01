package com.bugquery.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.osgi.service.prefs.Preferences;

import com.bugquery.markers.ResourcesUtils;

/**
 * Dynamic item for choose project menu
 * @author Doron
 * @since 28 05 2017
 */
public class DynamicItem extends CompoundContributionItem {

	public DynamicItem() {
	}

	public DynamicItem(String id) {
		super(id);
	}

	@Override
	public void fill(Menu m, int index) {
		Preferences prefs = InstanceScope.INSTANCE.getNode(
				"com.bugquery.preferences");
		for (IProject p : ResourcesUtils.getOpenedProjects()) {
			MenuItem menuItem = new MenuItem(m, SWT.RADIO, index);
			menuItem.setText(p.getName());
			if (prefs.get("projectname", "default").equals(p.getName()))
				menuItem.setSelection(true); 
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent __) {
					prefs.put("projectname", p.getName());
				}
			});
		}
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		return null;
	}

}
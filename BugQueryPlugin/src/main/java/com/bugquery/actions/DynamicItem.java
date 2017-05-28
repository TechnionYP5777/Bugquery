package com.bugquery.actions;

import java.util.HashMap;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import com.bugquery.markers.ResourcesUtils;

public class DynamicItem extends CompoundContributionItem {

	public DynamicItem() {
	}

	public DynamicItem(String id) {
		super(id);
	}

	// @Override
	// public void fill(Menu menu, int index) {
	// // Here you could get selection and decide what to do
	// // You can also simply return if you do not want to show a menu
	//
	// // create the menu item
	// MenuItem menuItem = new MenuItem(menu, SWT.RADIO, index);
	// menuItem.setText(
	// ResourcesUtils.getOpenedProjects().get(index).getName());
	// menuItem.addSelectionListener(new SelectionAdapter() {
	// public void widgetSelected(SelectionEvent e) {
	// System.out.println("Dynamic menu selected");
	// }
	// });
	// }

	@Override
	protected IContributionItem[] getContributionItems() {
		Integer size = ResourcesUtils.getOpenedProjects().size();
		IContributionItem[] $ = new IContributionItem[size];
		for (int i = 0; i < size; ++i) {
			$[i] = createItem(i);
		}
		return $;
	}

	private IContributionItem createItem(int index) {

		CommandContributionItemParameter param = new CommandContributionItemParameter(
				PlatformUI.getWorkbench(), null,
				"com.bugquery.commands.dynamicitem",
				CommandContributionItem.STYLE_PUSH);
//		param.parameters = new HashMap<String, String>();
//		param.parameters.put("commandParameterID", "TheValue");
		param.visibleEnabled = true;
		param.label = ResourcesUtils.getOpenedProjects().get(index).getName(); 

		return new CommandContributionItem(param);
	}

	@Override
	public boolean isDynamic() {
		return true;
	}
}
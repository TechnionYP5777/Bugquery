package com.bugquery.console;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

import com.bugquery.actions.Dispatch;

/**
 * A button action, sends @console's contents to Dispatch
 * 
 * @author Yosef
 * @since May 10, 2017
 */
@SuppressWarnings("restriction")
public class ConsoleQuery extends Action {
	TextConsole console;
	static ImageDescriptor icon;
	static {
		// initializing icon
		Bundle bundle = Platform.getBundle("com.bugquery.plugin");
		URL imageURL = BundleUtility.find(bundle, "icons/bugquery_main.gif");
		icon = ImageDescriptor.createFromURL(imageURL);
	}
	
	public ConsoleQuery(TextConsole console) {
		super("Send to BugQuery", icon);
		this.console = console;
	}

	/**
	 * on click: call Dispatch.query() with the contents of the button's console console 
	 */
	@Override
	public void run() {
		if (console == null)
			return;
		String input = console.getDocument().get();
		Dispatch.query(input);
	}

}
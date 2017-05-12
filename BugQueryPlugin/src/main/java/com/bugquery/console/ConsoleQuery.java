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
 * sends @console's contents to Dispatch
 * 
 * @author Yosef
 * @since May 10, 2017
 */
public class ConsoleQuery extends Action {
	TextConsole console;
	static ImageDescriptor icon;
	static {
		Bundle bundle = Platform.getBundle("com.bugquery.plugin");
		URL imageURL = BundleUtility.find(bundle, "icons/bugquery_main.gif");
		icon = ImageDescriptor.createFromURL(imageURL);
	}
	
	public ConsoleQuery(TextConsole console) {
		super("Send to BugQuery", icon);
		this.console = console;
	}

	@Override
	public void run() {
		if (console != null) {
			String input = console.getDocument().get();
			Dispatch.query(input);
		}
	}

}
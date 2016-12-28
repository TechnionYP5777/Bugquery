package com.bugquery.stacktrace;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Locale;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A temporary dialog, used for pasting using the system paste instead of java
 * implementation.
 * 
 * @author Yosef
 *
 */
public class ClipboardDialog extends InputDialog {
	public ClipboardDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
	}

	protected Control createDialogArea(Composite parent) {
		Control $ = super.createDialogArea(parent);
		Text dialog_input = this.getText();
		((GridData) dialog_input.getLayoutData()).heightHint = 200;

		dialog_input.addFocusListener(new FocusListener() {
			boolean first = true;
			int key_mask = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH).indexOf("mac") >= 0
					? KeyEvent.VK_META : KeyEvent.VK_CONTROL;

			public void focusLost(FocusEvent __) {
			}

			public void focusGained(FocusEvent __) {
				if (!first)
					return;
				Robot robot;
				try {
					robot = new Robot();
					robot.keyPress(key_mask);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(key_mask);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
				first = false;
			}
		});
		return $;
	}

}

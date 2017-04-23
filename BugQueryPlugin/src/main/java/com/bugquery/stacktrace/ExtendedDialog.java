package com.bugquery.stacktrace;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * ExtendedDialog extends InputDialog: it uses a larger Text() element, that
 * supports input with multiple lines.
 *
 * @author Yosef
 * @since Dec 8, 2016
 *
 */
public class ExtendedDialog extends InputDialog {
	public ExtendedDialog(final Shell parentShell, final String dialogTitle, final String dialogMessage,
			final String initialValue, final IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	@Override
	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Control $ = super.createDialogArea(parent);
		((GridData) getText().getLayoutData()).heightHint = 200;
		return $;
	}
}

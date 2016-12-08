package bugquery.stacktrace;

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
 */
public class ExtendedDialog extends InputDialog {
	public ExtendedDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
	}

	protected Control createDialogArea(Composite parent) {
		Control $ = super.createDialogArea(parent);
		((GridData) getText().getLayoutData()).heightHint = 200;
		return $;
	}
}

package old;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover;

/**
 * Initial prototype for our window, it's harder then it looks...
 * @author Doron Meshulam
 * @since 05/05/2017
 */
public class BQTextHover implements IJavaEditorTextHover {
	@SuppressWarnings("unused")
	private IEditorPart editor;
	
	public BQTextHover() {
	}
	
	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		try {
			String text = textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
			return text + "<br> <b>" + text +"</b>";
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Unidentified";
		}
	}
	
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return null;
	}

	@Override
	public void setEditor(IEditorPart arg0) {
		this.editor = arg0; 
	}
}

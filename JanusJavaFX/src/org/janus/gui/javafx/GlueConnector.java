/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.scene.layout.Region;

import org.janus.gui.enums.GuiType;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class GlueConnector extends JavaFXBasisConnector {

	/**
	 * Constructor declaration
	 * 
	 * 
	 * @param node
	 * @param name
	 * @param model
	 * 
	 * @see
	 */
	public GlueConnector(Region spacer) {
		super(GuiType.GLUE, spacer);
		//((JComponent)glue).setAlignmentX(Component.LEFT_ALIGNMENT);
		
	}

	@Override
	protected void setGuiValueWithText(String text) {
		
	}

	@Override
	public Serializable getGuiValue() {
		return "";
	}

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


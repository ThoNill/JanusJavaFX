/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import org.janus.gui.enums.GuiType;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class VBoxConnector extends JavaFXBasisConnector {

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
    public VBoxConnector(VBox panel) {
        super(GuiType.VBOX, panel);
        panel.setAlignment(Pos.BOTTOM_LEFT);
    }

    public VBox getBox() {
        return (VBox) getComponent();
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


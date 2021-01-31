/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import org.janus.gui.enums.GuiType;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class HBoxConnector extends JavaFXBasisConnector {

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
    public HBoxConnector(HBox panel) {
        super(GuiType.HBOX, panel);
        panel.setAlignment(Pos.CENTER_LEFT);

    }

    public HBox getBox() {
        return (HBox) getComponent();
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


/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.scene.control.Label;

import org.janus.gui.enums.GuiType;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class LabelConnector extends JavaFXBasisConnector {

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
    public LabelConnector(Label label) {
        super(GuiType.LABEL, label);
        // label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public Label getJavaFXLabel() {
        return (Label) getComponent();
    }

    @Override
    protected void setGuiValueWithText(String text) {
        if (text != null) {
            getJavaFXLabel().setText(text);
        }
    }

    @Override
    public Serializable getGuiValue() {
        return getJavaFXLabel().getText();
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.awt.Dimension;
import java.io.Serializable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import org.janus.gui.enums.GuiType;

import com.sun.javafx.tk.FontMetrics;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class CheckBoxConnector extends JavaFXBasisConnector implements
        ChangeListener<Boolean> {

    public CheckBoxConnector(CheckBox box) {
        super(GuiType.CHECK, box);
        box.selectedProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> arg0,
            Boolean oldValue, Boolean newValue) {
        setModelValue("" + newValue);
    }

    @Override
    protected void setGuiValueWithText(String text) {
        boolean b = Boolean.parseBoolean(text);
        if (getCheckBox() != null) {
            getCheckBox().setSelected(b);
        }
    }

    @Override
    protected Dimension getDefaultDimension() {
        FontMetrics fm = getFontMetrics();

        int w = (int) fm.computeStringWidth("XXXXXXXXX");
        int h = (int) (fm.getLineHeight() * 1.2f);
        return new Dimension(w, h);
    }

    CheckBox getCheckBox() {
        return (CheckBox) getComponent();
    }

    @Override
    public Serializable getGuiValue() {
        return getCheckBox().isSelected();
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


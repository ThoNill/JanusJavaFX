/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.awt.Dimension;
import java.io.Serializable;

import org.janus.gui.enums.GuiType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.CheckBox;
import metric.FontMetrics;


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

        Bounds b = fm.bounds("XXXXXXXXX");
        int h = (int) (b.getHeight() * 1.2d);
        return new Dimension((int)b.getWidth(), h);
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


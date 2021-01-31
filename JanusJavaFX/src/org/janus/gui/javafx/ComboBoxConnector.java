/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.janus.gui.enums.GuiType;
import org.janus.table.ExtendedTableModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class ComboBoxConnector extends JavaFXTableModelConnector implements
        ChangeListener {
    private static final Logger LOG = Logger.getLogger(ComboBoxConnector.class);

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
    public ComboBoxConnector(ComboBox<String> list) {
        super(GuiType.LIST, list);
        list.getSelectionModel().selectedItemProperty().addListener(this);
        // initSelectionModel();
    }

    public ComboBox<String> getList() {
        return (ComboBox<String>) getComponent();
    }

    @Override
    public void SelectionChanged(int pos, int column) {
        int viewPos = getSelectedIndex();
        int modelPos = getTableModel().getCurrentRow();
        //if (viewPos != modelPos) {
            getList().getSelectionModel().select(modelPos);
        //}
    }

    @Override
    public void changed(ObservableValue arg0, Object arg1, Object arg2) {
        int viewPos = getSelectedIndex();
        int modelPos = getTableModel().getCurrentRow();
        if (viewPos != modelPos) {
            setCurrentRowInTheModel(viewPos);
        }
    }

    @Override
    public void setModel(ExtendedTableModel tm) {
        try {
            ObservableList<String> m = new TableModelList(getTableModel());
            if (getList() != null) {
                getList().setItems(m);
         //       getList().setSelectedIndex(tm.getCurrentRow());
            }
        } catch (Exception ex) {
            LOG.error("Fehler", ex);

        }
    }

    @Override
    public Serializable getGuiValue() {
        return (Serializable) getTableModel().getValueAt(getSelectedIndex(), 0);
    }

    protected int getSelectedIndex() {
        return getList().getSelectionModel().getSelectedIndex();
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


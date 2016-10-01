package org.janus.gui.javafx;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class TableModelCellValueFactory
        implements
        Callback<TableColumn.CellDataFeatures<TableModelRow, String>, ObservableValue<String>> {

    @Override
    public ObservableValue<String> call(
            CellDataFeatures<TableModelRow, String> arg0) {
        Object obj = arg0.getValue().getValue(
                ((Integer) arg0.getTableColumn().getUserData()).intValue());
        String text = (obj == null) ? "" : obj.toString();
        return new SimpleObjectProperty<String>(text);
    }

}

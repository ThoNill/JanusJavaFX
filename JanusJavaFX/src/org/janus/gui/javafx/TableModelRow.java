package org.janus.gui.javafx;

import javax.swing.table.TableModel;

public class TableModelRow {
    private int row;
    private TableModel model;

    public TableModelRow(int row, TableModel model) {
        super();
        this.row = row;
        this.model = model;
    }

    public Object getValue(int columnIndex) {
        return model.getValueAt(row, columnIndex);
    }
}

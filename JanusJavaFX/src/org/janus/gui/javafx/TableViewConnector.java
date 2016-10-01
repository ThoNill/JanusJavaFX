/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.enums.GuiType;
import org.janus.table.ExtendedTableModel;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class TableViewConnector extends JavaFXTableModelConnector implements
        ChangeListener {

    /**
     * Constructor declaration
     * 
     * 
     * @param node
     * @param name
     * @param action
     * 
     * @see
     */
    public TableViewConnector(TableView table,
            List<TableColumnDescription> columnDescription) {
        super(GuiType.SHOWTABLE, table);
        createColumnsFromDescriptions(table, columnDescription);
        initSelectionModel();

    }

    public TableView getTableView() {
        return (TableView) getComponent();
    }

    private void createColumnsFromDescriptions(TableView table,
            List<TableColumnDescription> columnDescription) {
        // table.setAutoCreateColumnsFromModel(false);
        // TableColumnModel cm = table.getColumnModel();
        int index = 0;
        for (TableColumnDescription desc : columnDescription) {
            createColumnFromDescription(table, desc, index);
            index++;
        }
    }

    private void createColumnFromDescription(TableView cm,
            TableColumnDescription desc, int index) {
        TableColumn column = new TableColumn(desc.getHeader()); // column.getIndex());
        column.setUserData(new Integer(index));
        column.setCellValueFactory(new TableModelCellValueFactory());
        cm.getColumns().add(column);
    }

    private void initSelectionModel() {
        getTableView().getSelectionModel().selectedItemProperty()
                .addListener(this);
    }

    @Override
    public void SelectionChanged(int pos) {
        int oldPos = getTableView().getSelectionModel().getSelectedIndex();
        if (oldPos != pos) {
            getTableView().getSelectionModel().select(pos);
        }

    }

    @Override
    public Serializable getGuiValue() {
        return getTableModel();
    }

    @Override
    public void changed(ObservableValue arg0, Object arg1, Object arg2) {
        int row = getTableView().getSelectionModel().getSelectedIndex();
        setCurrentRowInTheModel(row);
    }

    @Override
    public void setModel(ExtendedTableModel tm) {
        if (tm != null) {

            List<TableModelRow> list = new ArrayList<TableModelRow>();
            int anz = getTableModel().getRowCount();
            for (int i = 0; i < anz; i++) {
                list.add(new TableModelRow(i, getTableModel()));
            }
            ObservableList<TableModelRow> data = FXCollections
                    .observableList(list);

            getTableView().setItems(data);
        }
    }

    @Override
    public Control decorate() {
        return new ScrollPane(getControl());
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


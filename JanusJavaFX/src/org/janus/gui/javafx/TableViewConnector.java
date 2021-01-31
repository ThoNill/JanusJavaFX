/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.enums.GuiType;
import org.janus.table.ExtendedTableModel;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class TableViewConnector extends JavaFXTableModelConnector implements
        ListChangeListener<TablePosition> {

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
        table.getSelectionModel().setCellSelectionEnabled(true);
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
        getTableView().getSelectionModel().getSelectedCells().addListener(this);
        
    }

    @Override
    public void SelectionChanged(int pos, int column) {
        int oldPos = getTableView().getSelectionModel().getSelectedIndex();
        if (oldPos != pos) {
            getTableView().getSelectionModel().select(pos);
            TableColumn tc = (TableColumn)getTableView().getColumns().get(column);
            getTableView().getSelectionModel().select(pos, tc);
            // TODO Auswahl der Ccolum
        }

    }

    @Override
    public Serializable getGuiValue() {
        return getTableModel();
    }


    protected void onViewChange() {
        int row = getTableView().getSelectionModel().getSelectedIndex();
        setCurrentRowInTheModel(row);
        ObservableList<TablePosition> cells = getTableView()
                .getSelectionModel().getSelectedCells();
        if (cells != null && !cells.isEmpty()) {
            setCurrentColumnInTheModel(cells.get(0).getColumn());
        } else {
            setCurrentColumnInTheModel(0);
        }
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
    public void setGuiValue(Serializable tm) {
        super.setGuiValue(tm);
        if (tm instanceof ExtendedTableModel) {
            updateModel((ExtendedTableModel) tm);
        }
    }

    public void updateModel(ExtendedTableModel tm) {
        if (tm != null) {
            TableView tableView = getTableView();
            ((TableColumn) tableView.getColumns().get(0)).setVisible(false);
            ((TableColumn) tableView.getColumns().get(0)).setVisible(true);
        }
    }

    @Override
    public Control decorate() {
        return new ScrollPane(getControl());
    }

    @Override
    public void onChanged(
            javafx.collections.ListChangeListener.Change<? extends TablePosition> arg0) {
          onViewChange();      
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


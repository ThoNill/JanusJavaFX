/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import org.janus.gui.enums.GuiType;
import org.janus.table.ExtendedTableModel;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class ListViewConnector extends SwtTableModelConnector implements
		ChangeListener {

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
	public ListViewConnector(ListView<String> list) {
		super(GuiType.LIST, list);
		list.getSelectionModel().selectedItemProperty().addListener(this);
		// initSelectionModel();
	}

	public ListView<String> getList() {
		return (ListView<String>) getComponent();
	}

	@Override
	public void SelectionChanged(int pos) {
		getList().getSelectionModel().select(getTableModel().getCurrentRow());
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		setCurrentRowInTheModel(getSelectedIndex());
	}

	@Override
	public void setModel(ExtendedTableModel tm) {
		try {

			ObservableList<String> m = new TableModelList(getTableModel());
			if (getList() != null) {
				getList().setItems(m);
				getList().getSelectionModel().select(tm.getCurrentRow());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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


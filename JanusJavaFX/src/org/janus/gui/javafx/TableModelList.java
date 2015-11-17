/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import javafx.collections.ObservableListBase;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;
import org.janus.table.ExtendedTableModel;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */

public class TableModelList extends ObservableListBase<String> implements
		TableModelListener {

	ExtendedTableModel model = null;

	static Logger logger = Logger.getLogger("SwingComboBoxModel");

	/**
	 * Constructor declaration
	 * 
	 * 
	 * @param model
	 * 
	 * @see
	 */
	public TableModelList(ExtendedTableModel model) {
		this.model = model;
		model.addTableModelListener(this);

	}

	@Override
	public void tableChanged(TableModelEvent e) {

		beginChange();
		endChange();

	}

	@Override
	public String get(int index) {
		if (model.getColumnCount() < 2) {
			return convert(index,0);
		}

		return convert(index, 1);
	}

	protected String convert(int index,int column) {
		Object obj = model.getValueAt(index, column);
		return (obj==null) ? "" : obj.toString();
	}

	@Override
	public int size() {
		return model.getRowCount();
	}

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/

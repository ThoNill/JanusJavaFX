/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;

import org.janus.gui.enums.GuiType;
import org.janus.table.ExtendedTableModel;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class RadioConnector extends JavaFXTableModelConnector implements
		ChangeListener<Toggle> {
	
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
	public RadioConnector(RadioGroup list) {
		super(GuiType.RADIO, list);
		list.addSelectionListener(this);
	}

	public RadioGroup getRadioGroup() {
		return (RadioGroup) getComponent();
	}

	@Override
	public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1,
			Toggle arg2) {
		setCurrentRowInTheModel(getRadioGroup().getSelectedIndex());
	}

	@Override
	public void SelectionChanged(int pos) {
		getRadioGroup().setSelectedIndex(pos);
	}

	@Override
	public void setModel(ExtendedTableModel tm) {
		getRadioGroup().setModel(getTableModel());
	}

	@Override
	public Serializable getGuiValue() {
		return getRadioGroup().getSelectedIndex();
	}



}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


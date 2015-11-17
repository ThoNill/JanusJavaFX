package org.janus.gui.javafx;

import java.io.Serializable;

import org.janus.dict.actions.NamedActionValue;
import org.janus.gui.enums.GuiType;
import org.janus.table.DefaultExtendedTableWrapper;
import org.janus.table.ExtendedTableModel;

public abstract class SwtTableModelConnector extends JavaFXBasisConnector {

	private ExtendedTableModel tableModel = null;
	private int lastRow = -1;
	protected DefaultExtendedTableWrapper tableWrapper;

	public SwtTableModelConnector(GuiType type, Object component) {
		super(type, component);
	}

	public abstract void SelectionChanged(int pos);

	@Override
	public void setValue(NamedActionValue value) {
		super.setValue(value);
		if (value.getAction() instanceof DefaultExtendedTableWrapper) {
			tableWrapper = (DefaultExtendedTableWrapper) value.getAction();
			tableWrapper.getCurrentRow().addActionListener(this);
		}
	}

	private int getCurrentRowInTheModel() {
		return tableWrapper.getCurrentRow(context);

	}

	void setCurrentRowInTheModel(int selectedRow) {
		try {
			tableWrapper.setCurrentRow(context, selectedRow);
			performAllActions();
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@Override
	public void setGuiValue(Serializable tm) {

		if (tm instanceof ExtendedTableModel) {
			ExtendedTableModel aktuellesModel = (ExtendedTableModel) tm;
			if (tableModel == null || !tableModel.equals(aktuellesModel)) {
				tableModel = aktuellesModel;
				setModel(aktuellesModel);
				lastRow = -1;
			}

			int aktuelleRow = getCurrentRowInTheModel();
			if (lastRow != aktuelleRow) {
				lastRow = aktuelleRow;
				SelectionChanged(aktuelleRow);
			}
		}
		if (tm instanceof Integer) {
			int aktuelleRow = ((Integer) tm).intValue();
			if (lastRow != aktuelleRow) {
				lastRow = aktuelleRow;
				SelectionChanged(aktuelleRow);
			}
		}
	}

	public abstract void setModel(ExtendedTableModel tm);

	public ExtendedTableModel getTableModel() {
		return tableModel;
	}

}
package org.janus.gui.javafx;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.janus.dict.actions.NamedActionValue;
import org.janus.gui.enums.GuiType;
import org.janus.table.DefaultExtendedTableWrapper;
import org.janus.table.ExtendedTableModel;

public abstract class JavaFXTableModelConnector extends JavaFXBasisConnector {
    private static final Logger LOG = Logger
            .getLogger(JavaFXTableModelConnector.class);

    private ExtendedTableModel tableModel = null;
    private int lastRow = -1;
    protected DefaultExtendedTableWrapper tableWrapper;

    public JavaFXTableModelConnector(GuiType type, Object component) {
        super(type, component);
    }

    public abstract void SelectionChanged(int pos, int column);

    @Override
    public void setValue(NamedActionValue value) {
        super.setValue(value);
        if (value.getAction() instanceof DefaultExtendedTableWrapper) {
            tableWrapper = (DefaultExtendedTableWrapper) value.getAction();
            tableWrapper.getCurrentRow().addActionListener(this);
            tableWrapper.getCurrentColumn().addActionListener(this);
            tableWrapper.getCurrentValue().addActionListener(this);
        }
    }

    private int getCurrentRowInTheModel() {
        return tableWrapper.getCurrentRow(context);

    }

    void setCurrentRowInTheModel(int selectedRow) {
        try {
            int oldRow = getCurrentRowInTheModel();
            if (oldRow != selectedRow) {
                tableWrapper.setCurrentRow(context, selectedRow);
                performAllActions();
            }
        } catch (Exception ex) {
            LOG.error("Fehler", ex);
        }

    }

    private int getCurrentColumnInTheModel() {
        return tableWrapper.getCurrentColumn(context);
    }

    void setCurrentColumnInTheModel(int selectedColumn) {
        try {
            if (selectedColumn < 0) {
                selectedColumn = Math.max(0,
                        tableWrapper.getCurrentColumn(context) - 1);
            }
            int oldColumn = getCurrentColumnInTheModel();
            if (oldColumn != selectedColumn) {
                tableWrapper.setCurrentColumn(context, selectedColumn);
                performAllActions();
            }
            ;
        } catch (Exception ex) {
            LOG.error("Fehler", ex);
        }
    }

    @Override
    public void setGuiValue(Serializable tm) {
        if (tm instanceof ExtendedTableModel) {
            tm = tableWrapper.getTable(context);
            ExtendedTableModel aktuellesModel = (ExtendedTableModel) tm;
            if (tableModel == null || !tableModel.equals(aktuellesModel)) {
                tableModel = aktuellesModel;
                setModel(aktuellesModel);
                lastRow = -1;
            }

            int aktuelleRow = getCurrentRowInTheModel();
            if (lastRow != aktuelleRow) {
                lastRow = aktuelleRow;
                SelectionChanged(aktuelleRow, getCurrentColumnInTheModel());
            }
        } else if (tm instanceof Integer) {
            int aktuelleRow = ((Integer) tm).intValue();
            if (lastRow != aktuelleRow) {
                lastRow = aktuelleRow;
                SelectionChanged(aktuelleRow, getCurrentColumnInTheModel());
            }
        }
    }

    public abstract void setModel(ExtendedTableModel tm);

    public ExtendedTableModel getTableModel() {
        return tableModel;
    }

}
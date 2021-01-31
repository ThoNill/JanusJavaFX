package org.janus.gui.javafx;

import java.io.Serializable;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.enums.GuiType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabbedPaneConnector extends JavaFXBasisConnector implements
        ChangeListener<Number> {

    TabPane pane;

    public TabbedPaneConnector(TabPane pane) {
        super(GuiType.TABS, pane);
        this.pane = pane;
        SingleSelectionModel<Tab> selection = pane.getSelectionModel();
        selection.selectedIndexProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1,
            Number arg2) {
        if (context != null) {
            SingleSelectionModel<Tab> selection = getTabbedPane()
                    .getSelectionModel();
            String viewPos = "" + selection.getSelectedIndex();
            String modelPos = value.getObject(context).toString();
            if (!viewPos.equals(modelPos)) {
                value.setObject(context, viewPos);
            }
        }
    }

    @Override
    protected void setGuiValueWithText(String text) {
        SingleSelectionModel<Tab> selection = getTabbedPane()
                .getSelectionModel();
        int pos = Integer.parseInt(text);
        int viewPos = getTabbedPane().getSelectionModel().getSelectedIndex();
        if (selection != null && pos >= 0 && pos < getTabbedPane().getTabs().size() ) {
            selection.select(pos);
        }

    }

    private TabPane getTabbedPane() {
        return (TabPane) getComponent();
    }

    @Override
    public Serializable getGuiValue() {
        if (getTabbedPane() != null) {
            return getTabbedPane().getSelectionModel().getSelectedIndex();
        }
        return -1;
    }

    @Override
    public void addComponent(GuiComponent comp) {
        if (comp instanceof TabConnector) {
            TabConnector childConnector = (TabConnector) comp;
            pane.getTabs().add(childConnector.getTab());
        }
    }

}

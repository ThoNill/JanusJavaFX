package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.enums.GuiType;

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
            value.setObject(context, "" + selection.getSelectedIndex());
        }
    }

    @Override
    protected void setGuiValueWithText(String text) {
        SingleSelectionModel<Tab> selection = getTabbedPane()
                .getSelectionModel();
        int i = Integer.parseInt(text);
        if (selection != null && i >= 0 && i < getTabbedPane().getTabs().size()) {
            selection.select(i);
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

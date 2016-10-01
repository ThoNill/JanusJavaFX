package org.janus.gui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.janus.table.ExtendedTableModel;

public class RadioGroup extends StackPane implements EventHandler<ActionEvent> {
    ToggleGroup group;
    Pane subPane;

    public RadioGroup() {
        super();
        // setAlignmentX(Component.LEFT_ALIGNMENT);
        subPane = new VBox();
        this.getChildren().add(subPane);
        init();
    }

    public void init() {
        group = new ToggleGroup();
    }

    public void addEntry(Integer value, String text, boolean selectIt) {
        RadioButton b = new RadioButton(text);
        b.setUserData(value);
        // b.onActionProperty().addListener(l);
        b.setToggleGroup(group);
        subPane.getChildren().add(b);
        b.setSelected(selectIt);
    }

    public void setModel(ExtendedTableModel model) {
        removeButtons();
        addButtons(model);
    }

    private void addButtons(ExtendedTableModel model) {
        boolean isSelected = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            isSelected = (i == model.getCurrentRow());
            // String value = model.getValueAt(i, 0).toString();
            String text = model.getValueAt(i, 1).toString();
            addEntry(i, text, isSelected);
        }

    }

    private void removeButtons() {
        List<Toggle> list = new ArrayList<>(group.getToggles());
        for (Toggle t : list) {
            t.setToggleGroup(null);
            // ((RadioButton)t).onActionProperty().removeListener(l);
            subPane.getChildren().remove(t);
        }
    }

    public void setSelectedIndex(int pos) {
        group.getToggles().get(pos).setSelected(true);
    }

    public int getSelectedIndex() {
        Integer i = (Integer) group.getSelectedToggle().getUserData();
        return i.intValue();
    }

    @Override
    public void handle(ActionEvent arg0) {
    }

    public void addSelectionListener(ChangeListener<? super Toggle> l) {
        group.selectedToggleProperty().addListener(l);
    }
}
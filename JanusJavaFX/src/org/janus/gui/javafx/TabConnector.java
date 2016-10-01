package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.janus.gui.enums.GuiType;

public class TabConnector extends JavaFXBasisConnector implements
        ChangeListener {

    private Tab tab;
    private Pane pane;
    private String name;

    public TabConnector(Pane pane, String name) {
        super(GuiType.TABS, pane);
        this.name = name;
        this.pane = pane;
        this.tab = new Tab(name);
        this.tab.setContent(pane);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
    }

    @Override
    public Serializable getGuiValue() {
        return null;
    }

    protected String getName() {
        return name;
    }

    public Tab getTab() {
        return tab;
    }

}

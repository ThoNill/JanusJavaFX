/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

import org.janus.actions.Action;
import org.janus.gui.enums.GuiType;

import allgemein.SessionInterface;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class MenuItemConnector extends JavaFXBasisConnector implements
        EventHandler<ActionEvent> {
    Action action;

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
    public MenuItemConnector(MenuItem item, Action action) {
        super(GuiType.MENUITEM, item);
        this.action = action;
        item.setOnAction(this);

    }

    public MenuItem getMenuItem() {
        return (MenuItem) getComponent();
    }

    @Override
    public void setGuiValue(Serializable text) {
        if (text != null) {
            getMenuItem().setText((String) text);
        }
    }

    @Override
    public Serializable getGuiValue() {
        return getMenuItem().getText();
    }

    @Override
    public void handle(ActionEvent arg0) {
        SessionInterface.performAction(action, context);
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


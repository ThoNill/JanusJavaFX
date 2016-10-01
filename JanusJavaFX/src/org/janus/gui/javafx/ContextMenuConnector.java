/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.enums.GuiType;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class ContextMenuConnector extends JavaFXBasisConnector implements
        java.awt.event.ActionListener {

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
    public ContextMenuConnector(ContextMenu menu) {
        super(GuiType.POPUP, menu);
    }

    public ContextMenu getMenu() {
        return (ContextMenu) getComponent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void setGuiValue(Serializable text) {
        if (text != null) {
            ObservableList<MenuItem> childs = getMenu().getItems();
            for (MenuItem n : childs) {
                if (text.equals(n.getText())) {
                    n.getGraphic().requestFocus();
                }
            }
        }
    }

    @Override
    public Serializable getGuiValue() {
        ObservableList<MenuItem> childs = getMenu().getItems();
        for (MenuItem n : childs) {
            if (n.getGraphic().isFocused()) {
                return n.getText();
            }
        }
        return null;
    };

    @Override
    public void addComponent(GuiComponent comp) {

        if (comp instanceof ContextMenuConnector) {
            ContextMenuConnector popup = (ContextMenuConnector) comp;
            ContextMenu popupMenu = popup.getMenu();
            getControl().setContextMenu(popupMenu);
        }

        if (childComponents == null) {
            childComponents = new ArrayList<>();
        }
        childComponents.add(comp);

        if (comp instanceof MenuItemConnector) {
            getMenu().getItems().add(((MenuItemConnector) comp).getMenuItem());
        }
        if (comp instanceof MenuConnector) {
            getMenu().getItems().add(((MenuConnector) comp).getMenu());
        }
    }
}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


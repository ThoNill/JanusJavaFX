/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

import org.janus.gui.basis.GuiComponent;
import org.janus.gui.enums.GuiType;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;

/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class MenuBarConnector extends JavaFXBasisConnector implements
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
    public MenuBarConnector(MenuBar bar) {
        super(GuiType.MENUBAR, bar);
    }

    public MenuBar getBar() {
        return (MenuBar) getComponent();
    };

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public Serializable getGuiValue() {
        return null;
    }

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

        if (comp instanceof MenuConnector) {
            getBar().getMenus().add(((MenuConnector) comp).getMenu());
        }
    }

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


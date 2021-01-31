package org.janus.gui.javafx;

import java.awt.Component;
import java.io.Serializable;
import java.util.List;

import org.janus.data.DataContext;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.RootGuiComponent;
import org.janus.gui.enums.GuiType;

import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageConnector extends JavaFXBasisConnector implements
        RootGuiComponent {
    Stage frame;
    Scene scene;
    Pane pane;

    private List<GuiComponent> components = null;

    public StageConnector(Stage frame) {
        super(GuiType.FRAME, frame);
        this.frame = frame;
        pane = new VBox();
        scene = new Scene(pane);
        frame.setScene(scene);
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    protected void setGuiValueWithText(String text) {
        if (text != null) {
            frame.setTitle(text);

        }
    }

    @Override
    public Object getComponent() {
        return frame;
    }

    @Override
    protected Component getUpdateComponent() {
        // return frame.getContentPane();
        return null;
    }

    @Override
    public Serializable getGuiValue() {
        return frame.getTitle();
    }

    @Override
    public void addComponent(GuiComponent comp) {
        if (comp instanceof MenuBarConnector) {
            setMenuBarConnector((MenuBarConnector) comp);
            return;
        }
        if (comp instanceof ContextMenuConnector) {
            ContextMenu contextMenu = ((ContextMenuConnector) comp).getMenu();

            pane.addEventHandler(
                    ContextMenuEvent.CONTEXT_MENU_REQUESTED,
                    event -> {
                        contextMenu.show(pane, event.getScreenX(),
                                event.getScreenY());
                        event.consume();
                    });
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                contextMenu.hide();
            });
            return;
        }
        if (comp instanceof JavaFXBasisConnector) {
            JavaFXBasisConnector childConnector = (JavaFXBasisConnector) comp;
            pane.getChildren().add(childConnector.getNode());
        }
    }

    private void setMenuBarConnector(MenuBarConnector barConnector) {
        pane.getChildren().add(0, barConnector.getBar());
    }

    @Override
    public List<GuiComponent> getAllComponents() {
        return components;
    }

    @Override
    public void setAllComponents(List<GuiComponent> components) {
        this.components = components;
    }

    @Override
    public void setContext(DataContext context) {
        super.setContext(context);
        for (GuiComponent c : components) {
            if (c != this) {
                ((JavaFXBasisConnector) c).setContext(context);
            }
        }
    }

}

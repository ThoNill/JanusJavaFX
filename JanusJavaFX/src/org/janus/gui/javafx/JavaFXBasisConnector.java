package org.janus.gui.javafx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import org.janus.data.DataContext;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.NamedActionValue;
import org.janus.dict.helper.ID;
import org.janus.dict.interfaces.ActionListener;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.enums.GuiField;
import org.janus.gui.enums.GuiType;

import probe.ColorAWT2Fx;
import allgemein.EventActionBinderList;

import com.sun.javafx.tk.Toolkit;

public abstract class JavaFXBasisConnector implements PropertyChangeListener,
        GuiComponent, ActionListener {

    protected Object component;
    private BorderedTitledPanel titleBorder = null;
    private String style;
    private int id;
    private GuiType type;
    protected NamedActionValue value;
    protected DataContext context;
    protected List<GuiComponent> childComponents = null;
    private java.awt.Font font;
    private javafx.scene.text.Font fxFont;
    private java.awt.Color background;
    private java.awt.Color foreground;
    private EventActionBinderList eventActionList;

    public JavaFXBasisConnector(GuiType type, Object component) {
        super();
        if (component == null) {
            throw new IllegalArgumentException(" Component component = null ");
        }
        id = ID.getId();
        this.type = type;
        this.component = component;

        /*
         * if (!(component != null && component instanceof Menu)) {
         * getNode().addPropertyChangeListener(this); }
         */
    }

    public BorderedTitledPanel getTitleBorder() {
        return titleBorder;
    }

    public void setTitleBorder(BorderedTitledPanel titleBorder) {
        this.titleBorder = titleBorder;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    public void setField(GuiField field, Serializable value) {
        switch (field) {
        case LABEL:
            setComponentLabel((String) value);
            break;
        case BACKGROUND:
            this.background = (Color) value;
            setBackgroundColor(this.background);
            break;
        case FOREGROUND:
            this.foreground = (Color) value;
            getControl().getShape().setFill(ColorAWT2Fx.convert(foreground));
            break;
        case FONT:
            this.font = (Font) value;
            this.fxFont = ColorAWT2Fx.convert(font);
            if (getControl() instanceof Labeled) {
                Labeled l = (Labeled) getControl();
                l.setFont(fxFont);
            }
            if (getControl() instanceof TextInputControl) {
                TextInputControl l = (TextInputControl) getControl();
                l.setFont(fxFont);
            }
            break;
        case ENABLED:
            getControl().setDisable(!((Boolean) value).booleanValue());
            break;
        case VISIBLE:
            getControl().setVisible(((Boolean) value).booleanValue());
            break;
        case FOCUS:
            if (value.equals(Boolean.TRUE)) {
                getControl().requestFocus();
            }
            break;
        case STYLE:
            setStyle((String) value);
            break;
        case TOOLTIP:
            getControl().setTooltip(new Tooltip((String) value));
            break;
        case WIDTH:
            getControl().setPrefWidth((int) value);
            break;
        case HEIGHT:
            getControl().setPrefHeight((int) value);
            break;
        case X:
            getControl().setLayoutX((int) value);
            break;
        case Y:
            getControl().setLayoutY((int) value);
            break;
        }
    }

    public void setBackgroundColor(Color background) {
        getControl()
                .setBackground(
                        new Background(new BackgroundFill(ColorAWT2Fx
                                .convert(background), CornerRadii.EMPTY,
                                Insets.EMPTY)));
    }

    private void setComponentLabel(String value) {
        if (value != null && !(component instanceof MenuItem)
                && !(component instanceof Menu)
                && !(component instanceof Button)
                && !(component instanceof Labeled)) {
            if (titleBorder == null) {
                titleBorder = new BorderedTitledPanel(value, (Node) component);
            }
        }

    }

    @Override
    public void setForeground(Color foreground) {
        setFieldInGuiThread(GuiField.FOREGROUND, foreground);
    }

    @Override
    public void setBackground(Color c) {
        setFieldInGuiThread(GuiField.BACKGROUND, c);

    }

    protected void setFieldInGuiThread(GuiField field, Serializable value) {
        if (Platform.isFxApplicationThread()) {
            setField(field, value);
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setField(field, value);
                }
            });
        }
    }

    @Override
    public void setGuiValue(Serializable v) {
        if (Platform.isFxApplicationThread()) {
            setGui(v);
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setGui(v);
                }
            });
        }

    }

    protected void setGui(Serializable v) {
        setGuiValueWithText(v.toString());
    }

    protected void setGuiValueWithText(String v) {

    }

    protected Object getObject() {
        return component;
    }

    protected Control getControl() {
        return (Control) component;
    }

    protected Pane getPane() {
        return (Pane) component;
    }

    protected Node getNode() {
        if (component instanceof Node) {
            return (Node) component;
        }
        return null;
    }

    protected Object getUpdateComponent() {
        return getComponent();
    }

    public void setSize(Dimension dim) {

        getControl().setPrefWidth(dim.getWidth());
        getControl().setPrefHeight(dim.getHeight());
        getControl().setMaxWidth(dim.getWidth());
        getControl().setMaxHeight(dim.getHeight());

    }

    protected Dimension getXDimension() {
        return calculateTextDimension("X");
    }

    protected Dimension calculateTextDimension(String text) {
        com.sun.javafx.tk.FontMetrics fm = getFontMetrics();
        return new Dimension((int) fm.computeStringWidth(text),
                (int) fm.getLineHeight());
    }

    protected com.sun.javafx.tk.FontMetrics getFontMetrics() {
        return Toolkit.getToolkit().getFontLoader().getFontMetrics(fxFont);
    }

    protected Dimension getDefaultDimension() {
        return new Dimension(10, 10);
    }

    protected Control decorate() {
        setSize(getDefaultDimension());
        return getControl();
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        setFieldInGuiThread(GuiField.FONT, font);
    }

    @Override
    public Color getForeground() {
        return foreground;
    }

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public void setEnabled(boolean b) {
        setFieldInGuiThread(GuiField.ENABLED, b);

    }

    @Override
    public boolean isEnabled() {
        return !getControl().isDisabled();
    }

    @Override
    public void setVisible(boolean b) {
        setFieldInGuiThread(GuiField.VISIBLE, b);
    }

    @Override
    public boolean isVisible() {
        return getControl().isVisible();
    }

    @Override
    public void setFocus(boolean b) {
        setFieldInGuiThread(GuiField.FOCUS, b);
    }

    @Override
    public boolean hasFocus() {
        return getControl().isFocused();
    }

    @Override
    public void setStyle(String t) {
        style = t;
    }

    @Override
    public String getStyle() {
        return style;
    }

    @Override
    public void setLabel(String t) {
        setFieldInGuiThread(GuiField.LABEL, t);
    }

    @Override
    public String getLabel() {
        if (titleBorder != null) {
            return titleBorder.getTitle();
        }
        return null;
    }

    @Override
    public void setTooltip(String t) {
        setFieldInGuiThread(GuiField.TOOLTIP, t);
    }

    @Override
    public String getTooltip() {
        if (getControl() != null && getControl().getTooltip() != null) {
            return getControl().getTooltip().getText();
        }
        return "";
    }

    @Override
    public void setWidth(float w) {
        setFieldInGuiThread(GuiField.WIDTH, new Float(w));

    }

    @Override
    public float getWidth() {
        return (float) getControl().getWidth();
    }

    @Override
    public void setHeight(float h) {
        setFieldInGuiThread(GuiField.HEIGHT, new Float(h));

    }

    @Override
    public float getHeight() {
        return (float) getControl().getHeight();
    }

    @Override
    public float getX() {
        return (float) getControl().getLayoutX();
    }

    @Override
    public void setX(float x) {
        setFieldInGuiThread(GuiField.X, new Float(x));
    }

    @Override
    public float getY() {
        return (float) getControl().getLayoutY();
    }

    @Override
    public void setY(float y) {
        setFieldInGuiThread(GuiField.Y, new Float(y));

    }

    public void setModelValue(Serializable v) {
        value.setObject(context, v);
        performAllActions();
    }

    public Serializable getModelValue() {
        return value.getObject(context);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void validate() {
    }

    @Override
    public GuiType getGuiType() {
        return type;
    }

    public DataContext getContext() {
        return context;
    }

    public void setContext(DataContext context) {
        this.context = context;
        if (eventActionList != null) {
            eventActionList.setContext(context);
        }
        updateValue();
    }

    protected void updateValue() {
        if (value != null && context != null) {
            setGuiValue(getModelValue());
        }
    }

    public NamedActionValue getValue() {
        return value;
    }

    public void setValue(NamedActionValue value) {
        this.value = value;
        value.addActionListener(this);
        updateValue();
    }

    @Override
    abstract public Serializable getGuiValue();

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

        if (comp instanceof ContextMenuConnector) {
            if (component instanceof Control) {
                getControl().setContextMenu(
                        ((ContextMenuConnector) comp).getMenu());
            }
        } else {
            if (comp instanceof JavaFXBasisConnector
                    && component instanceof Pane) {
                getPane().getChildren().add(
                        ((JavaFXBasisConnector) comp).getNode());
            }
        }
    }

    @Override
    public List<GuiComponent> getChildComponents() {
        if (childComponents == null) {
            return Collections.emptyList();
        }
        return childComponents;
    }

    @Override
    public void actionPerformed(Object a, DataContext data) {
        context = data;
        setGuiValue(getModelValue());
    }

    public void performAllActions() {
        ActionDictionary dict = (ActionDictionary) context.getDataDescription();
        dict.perform(context);
    }

    public void setEventActionList(EventActionBinderList eventActionList) {
        Node node = getNode();
        if (node != null && eventActionList.size() > 0) {
            this.eventActionList = eventActionList;
            eventActionList.register(node);
        }
    }

    public Object getComponent() {
        return component;
    }

}

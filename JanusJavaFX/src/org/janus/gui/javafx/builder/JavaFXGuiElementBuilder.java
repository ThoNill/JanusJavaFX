package org.janus.gui.javafx.builder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.StyleSheet;

import org.janus.actions.Action;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.NamedActionValue;
import org.janus.gui.basis.Attribut2GuiComponent;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.TableColumnDescription;
import org.janus.gui.builder.GuiElementBuilder;
import org.janus.gui.enums.GuiType;
import org.janus.gui.javafx.ButtonConnector;
import org.janus.gui.javafx.CheckBoxConnector;
import org.janus.gui.javafx.ComboBoxConnector;
import org.janus.gui.javafx.ContextMenuConnector;
import org.janus.gui.javafx.GlueConnector;
import org.janus.gui.javafx.HBoxConnector;
import org.janus.gui.javafx.JavaFXBasisConnector;
import org.janus.gui.javafx.LabelConnector;
import org.janus.gui.javafx.ListViewConnector;
import org.janus.gui.javafx.MenuBarConnector;
import org.janus.gui.javafx.MenuConnector;
import org.janus.gui.javafx.MenuItemConnector;
import org.janus.gui.javafx.RadioConnector;
import org.janus.gui.javafx.RadioGroup;
import org.janus.gui.javafx.StageConnector;
import org.janus.gui.javafx.TabConnector;
import org.janus.gui.javafx.TabbedPaneConnector;
import org.janus.gui.javafx.TableViewConnector;
import org.janus.gui.javafx.TextInputConnector;
import org.janus.gui.javafx.VBoxConnector;
import org.janus.table.DefaultExtendedTableWrapper;
import org.janus.table.DefaultTableColumn;
import org.jdom2.Element;

import allgemein.EventActionBinder;
import allgemein.EventActionBinderList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXGuiElementBuilder implements GuiElementBuilder {
	private static StyleSheet styleSheet = new StyleSheet();

	public JavaFXGuiElementBuilder() {

	}

	@Override
	public GuiComponent createGuiElement(Element elem, Action a, ActionDictionary dict) {
		GuiComponent comp = createGuiElementIntern(elem, a);
		Attribut2GuiComponent converter = new Attribut2GuiComponent();
		converter.setElementAttributes(comp, elem);
		EventActionBinderList list = getEventActionBinderList(elem);
		if (comp instanceof JavaFXBasisConnector) {
			JavaFXBasisConnector connector = (JavaFXBasisConnector) comp;
			connector.setEventActionList(list);

		}
		return comp;
	}

	private GuiComponent createGuiElementIntern(Element elem, Action a) {
		GuiType type = GuiType.valueOf(elem.getName());
		switch (type) {
		case GUI:
			return new StageConnector(new Stage());
		case GLUE:
			return createGlue(elem);
		case SHOWTABLE:
			return createTableConnector(elem, a);
		case VBOX:
			return createVBoxConnector(elem);
		case HBOX:
			return createHBoxConnector(elem);
		case TEXTFIELD:
			return createTextFieldConnector(elem, a);
		case MONEYFIELD:
			return createTextFieldConnector(elem, a);
		case DATEFIELD:
			return createTextFieldConnector(elem, a);
		case LABEL:
			return createLabelConnector(elem);
		case LIST:
			return createListConnector(elem, a);
		case COMBO:
			return createComboConnector(elem, a);
		case CHECK:
			return createCheckConnector(elem, a);
		case RADIO:
			return createRadioConnector(elem, a);
		case BUTTON:
			return createButtonConnector(elem, a);
		case TAB:
			return createTabConnector(elem, a);
		case TABS:
			return createTabsConnector(elem, a);
		case MENU:
			return new MenuConnector(new Menu(getTextFromElement(elem)));
		case MENUBAR:
			return new MenuBarConnector(new MenuBar());
		case MENUITEM:
			return new MenuItemConnector(new MenuItem(getTextFromElement(elem)), a);
		case POPUP:
			return new ContextMenuConnector(new ContextMenu());
		case FRAME:
			return null;

		}
		return null;
	}

	private GuiComponent createGlue(Element elem) {
		GuiType type = GuiType.valueOf(elem.getParentElement().getName());

		if (elem.getChildren().size() > 0) {
			StackPane pane = new StackPane();
			VBox.setVgrow(pane, Priority.ALWAYS);
			HBox.setHgrow(pane, Priority.ALWAYS);
			pane.setAlignment(Pos.CENTER);
			return new GlueConnector(pane);

		} else {

			if (type.equals(GuiType.VBOX)) {
				Region spacer = new Region();
				VBox.setVgrow(spacer, Priority.ALWAYS);
				return new GlueConnector(spacer);
			}
			if (type.equals(GuiType.HBOX)) {
				Region spacer = new Region();
				HBox.setHgrow(spacer, Priority.ALWAYS);
				return new GlueConnector(spacer);
			}
		}
		return null;
	}

	private ButtonConnector createButtonConnector(Element elem, Action a) {
		Button button = new Button(getTextFromElement(elem));
		return new ButtonConnector(button, a);
	}

	private HBoxConnector createHBoxConnector(Element elem) {
		HBox panel = new HBox();
		return new HBoxConnector(panel);
	}

	private VBoxConnector createVBoxConnector(Element elem) {
		VBox panel = new VBox();
		return new VBoxConnector(panel);
	}

	private LabelConnector createLabelConnector(Element elem) {
		Label label = new Label(getTextFromElement(elem));
		return new LabelConnector(label);
	}

	private GuiComponent createTabConnector(Element elem, Action a) {
		Pane panel = new VBox();
		TabConnector gui = new TabConnector(panel, elem.getAttributeValue("name"));
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createTabsConnector(Element elem, Action a) {
		TabPane pane = new TabPane();
		TabbedPaneConnector gui = new TabbedPaneConnector(pane);
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createTableConnector(Element elem, Action a) {
		List<TableColumnDescription> columnDescriptions = new ArrayList<>();
		boolean withColumns = false;
		for (Element e : elem.getChildren()) {
			if ("COLUMN".equals(e.getName())) {
				withColumns = true;
				columnDescriptions.add(new TableColumnDescription(e.getAttributeValue("renderer"),
						e.getAttributeValue("header"), e.getAttributeValue("name")));
			}
		}
		if (!withColumns && a instanceof DefaultExtendedTableWrapper) {
			DefaultExtendedTableWrapper wrapper = (DefaultExtendedTableWrapper) a;
			for (DefaultTableColumn c : wrapper.getColumns()) {
				columnDescriptions.add(new TableColumnDescription(c.getFormat().getClass().getName(), c.getColumnName(),
						c.getColumnName()));
			}
		}
		TableView table = new TableView();
		TableViewConnector gui = new TableViewConnector(table, columnDescriptions);
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createRadioConnector(Element elem, Action a) {
		RadioGroup group = new RadioGroup();
		RadioConnector gui = new RadioConnector(group);
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createCheckConnector(Element elem, Action a) {
		CheckBox box = new CheckBox();
		CheckBoxConnector gui = new CheckBoxConnector(box);
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createComboConnector(Element elem, Action a) {
		ComboBox<String> combo = new ComboBox<String>();
		ComboBoxConnector gui = new ComboBoxConnector(combo);
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createListConnector(Element elem, Action a) {
		ListView<String> list = new ListView<String>();
		ListViewConnector gui = new ListViewConnector(list);
		setValue(gui, a);
		return gui;
	}

	private GuiComponent createTextFieldConnector(Element elem, Action a) {
		TextField field = new TextField();
		/*
		 * field.setAlignmentX(Component.LEFT_ALIGNMENT);
		 * 
		 * Font f = new Font(Font.DIALOG_INPUT, Font.PLAIN, 12); field.setFont(f);
		 * FontMetrics fm = field.getFontMetrics(f); int h = (int) (fm.getHeight() *
		 * 1.4f); int w = fm.charWidth('X') * 10; Dimension dim = new Dimension(w, h);
		 * field.setSize(dim); field.setPreferredSize(dim); field.setMaximumSize(dim);
		 * field.setMinimumSize(dim);
		 * 
		 * attributeSetzen(elem, field);
		 */
		TextInputConnector gui = new TextInputConnector(field);
		setValue(gui, a);
		return gui;
	}

	private void setValue(JavaFXBasisConnector gui, Action a) {
		if (a instanceof NamedActionValue) {
			gui.setValue(((NamedActionValue) a));
		}
	}

	private String getTextFromElement(Element elem) {
		String t = elem.getAttributeValue("text");
		if (t == null) {
			t = "";
		}
		return t;
	}


	private EventActionBinderList getEventActionBinderList(Element elem) {
		EventActionBinderList list = new EventActionBinderList();
		for (Element e : elem.getChildren()) {
			if ("EVENT".equals(e.getName())) {
				EventActionBinder binder = new EventActionBinder(e.getAttributeValue("name"),
						e.getAttributeValue("action"));
				list.addElement(binder);
			}
		}
		return list;
	}

}

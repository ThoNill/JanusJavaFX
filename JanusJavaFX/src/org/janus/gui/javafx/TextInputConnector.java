package org.janus.gui.javafx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.Serializable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;

import org.janus.gui.enums.GuiType;
import org.janus.gui.enums.KeyEventType;

public class TextInputConnector extends JavaFXBasisConnector implements
		ChangeListener<Boolean>, EventHandler<ActionEvent> {
	private Background lastColor;
	private String lastValue;
	private boolean alreadyHasFocus = false;

	public TextInputConnector(TextField textField) {
		super(GuiType.TEXTFIELD, textField);
		textField.setOnAction(this);
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (KeyEventType.CTRL_D.name().equals(key)) {
					resetToOldValue();
				}
				if (KeyEventType.COPY.name().equals(key)) {
					setTextToClipboard(getTextfield().getText());
				}
				if (KeyEventType.PAST.name().equals(key)) {
					getTextfield().setText(getTextFromClipboard());
				}

			}
		});
		textField.focusedProperty().addListener(this);
		setGuiValueWithText("");
	}

	public TextInputControl getTextfield() {
		return (TextInputControl) getComponent();
	}

	@Override
	public void handle(ActionEvent arg0) {
		setModelValue();
	}

	protected void setModelValue() {
		setModelValue(getTextfield().getText());
	}

	@Override
	protected void setGuiValueWithText(String text) {
		if (text != null && getTextfield() != null) {
			getTextfield().setText(text);
		}
	}

	@Override
	protected void updateValue() {
		if (value != null && context != null) {
			Serializable s = getModelValue();
			setGuiValue(s);
		}
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> oldValue,
			Boolean newValue, Boolean arg2) {
		if (hasFocus()) {
			focusGained();
		} else {
			focusLost();
		}

	}

	public void focusGained() {
		if (!alreadyHasFocus) {
			alreadyHasFocus = true;
			lastColor = getTextfield().getBackground();
			setBackgroundColor(Color.yellow);
		}
	}

	public void focusLost() {
		if (alreadyHasFocus) {
			alreadyHasFocus=false;
			getTextfield().setBackground(lastColor);
			safeOldValue();
			setModelValue();
		}
	}

	public void safeOldValue() {
		lastValue = getTextfield().getText();
	}

	public void resetToOldValue() {
		getTextfield().setText(lastValue);
	}

	public static void setTextToClipboard(String text) {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection contents = new StringSelection(text);
		cb.setContents(contents, null);
	}

	public static String getTextFromClipboard() {
		String s = "";

		try {
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable content = cb.getContents(null);
			s = (String) content.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception ex) {
			// DebugAssistent.log(ex);
		}
		return s;
	}

	@Override
	protected Dimension getDefaultDimension() {
		com.sun.javafx.tk.FontMetrics fm = getFontMetrics();
		float a = getWidth();
		if (a <= 0) {
			a = 5.0f;
		}
		int w = (int) (fm.computeStringWidth("X") * a);
		int h = (int) (fm.getLineHeight() * 1.2f);
		return new Dimension(w, h);
	}

	@Override
	public Serializable getGuiValue() {
		return getTextfield().getText();
	}

}

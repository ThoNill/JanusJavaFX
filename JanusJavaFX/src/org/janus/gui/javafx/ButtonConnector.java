/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package org.janus.gui.javafx;

import java.awt.Dimension;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

import org.janus.actions.Action;
import org.janus.gui.enums.GuiField;
import org.janus.gui.enums.GuiType;

import allgemein.SessionInterface;


/**
 * Class declaration
 * 
 * 
 * @author
 * @version %I%, %G%
 */
public class ButtonConnector extends JavaFXBasisConnector implements EventHandler<ActionEvent>  {
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
	public ButtonConnector(Button box, Action action) {
		super(GuiType.BUTTON, box);
		this.action = action;
		box.setAlignment(Pos.CENTER);
		box.setOnAction(this);
	}

	public Button getButton() {
		return (Button) getComponent();
	}


	@Override
	public void setField(GuiField field, Serializable value) {

		switch (field) {
		case LABEL:
			getButton().setText((String)value);
			break;
		default:
			super.setField(field, value);
			break;
		}
	}

	@Override
	protected Dimension getDefaultDimension() {
		Dimension d = calculateTextDimension(getButton().getText());
		return new Dimension((int)(d.width * 1.4f),(int)( d.height * 1.3f));
	}

	@Override
	public void setGuiValueWithText(String  text) {
		getButton().setText(text);
	}

	@Override
	public Serializable getGuiValue() {
		return null;
	}

	@Override
	public void handle(ActionEvent arg0) {
		SessionInterface.performAction(action,context);
	}

}

/*--- formatting done in "Sun Java Convention" style on 11-06-2003 ---*/


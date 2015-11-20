package allgemein;

import java.awt.event.InputEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.dict.actions.ActionDictionary;
import org.janus.gui.enums.MouseEvents;

public class EventActionBinder {
	private String actionName;
	private Action action;
	private DataContext context;
	MouseEvents mouseEvent;
	JavaFXKeyEventType keyEvent = null;

	public EventActionBinder(String eventName, String actionName) {
		super();
		this.actionName = actionName;
		try {
			keyEvent = JavaFXKeyEventType.valueOf(eventName);
		} catch (Exception ex) {

		}
		try {
			mouseEvent = MouseEvents.valueOf(eventName);
		} catch (Exception ex) {

		}
	}

	public void register(Node node) {
		registerKeyEvent(node);

		registerMouseEvent(node);
	}

	protected void registerMouseEvent(Node node) {
		if (mouseEvent != null) {

			if (mouseEvent.equals(MouseEvents.MOUSE_IN)) {

				node.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						System.out.println("Mouse entered");
						doAction();
					}
				});
			}
			if (mouseEvent.equals(MouseEvents.MOUSE_OUT)) {
				node.setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						System.out.println("Mouse exited");
						doAction();
					}
				});
			}
			if (mouseEvent.equals(MouseEvents.MOUSE_CLICK)) {
				node.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						System.out.println("Mouse pressed");
						doAction();
					}
				});
			}
			if (mouseEvent.equals(MouseEvents.MOUSE_DOUBLECLICK)) {
				node.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						System.out.println("Mouse pressed");
						if (me.getClickCount() > 1) {
							doAction();
						}
					}
				});
			}
		}
	}

	protected void registerKeyEvent(Node node) {
		if (keyEvent != null) {

			node.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					System.out.println("Key Pressed: " + ke.getText());
					if (keyEvent.getKeyEvent().equals(ke.getCode())) {
						switch (keyEvent.getMask()) {
						case InputEvent.CTRL_MASK:
							if (ke.isControlDown()) {
								doAction();
							}
							break;
						case InputEvent.SHIFT_MASK:
							if (ke.isShiftDown()) {
								doAction();
							}
							break;
						default:
							doAction();
							break;
						}
					}
				}
			});
		}
	}

	public void actionPerformed(ActionEvent e) {
		doAction();
	}

	protected void doAction() {
		if (action == null) {
			action = ((ActionDictionary) context.getDataDescription())
					.getAction(actionName);
		}
		SessionInterface.performAction(action, context);
	}

	public void setContext(DataContext context) {
		this.context = context;
		action = ((ActionDictionary) context.getDataDescription())
				.getAction(actionName);
	}

}

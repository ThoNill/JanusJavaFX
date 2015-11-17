package allgemein;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.dict.actions.ActionDictionary;
import org.janus.gui.enums.KeyEventType;
import org.janus.gui.enums.MouseEvents;

public class EventActionBinder extends MouseAdapter implements ActionListener {
	private String eventName;
	private String actionName;
	private Action action;
	private DataContext context;
	MouseEvents mouseEvent;

	public EventActionBinder(String eventName, String actionName) {
		super();
		this.eventName = eventName;
		this.actionName = actionName;
	}

	public void register(JComponent component) {
		KeyEventType keyEvent = null;
		try {
			keyEvent = KeyEventType.valueOf(eventName);
		} catch (Exception ex) {

		}
		;
		if (keyEvent != null) {
			component
					.registerKeyboardAction(this, keyEvent.name(), KeyStroke
							.getKeyStroke(keyEvent.getKeyEvent(),
									keyEvent.getMask(), false),
							JComponent.WHEN_FOCUSED);
		}
		mouseEvent = null;
		try {
			mouseEvent = MouseEvents.valueOf(eventName);
		} catch (Exception ex) {

		}
		;
		if (mouseEvent != null) {
			component.addMouseListener(this);

		}
	}

	@Override
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1
				&& mouseEvent.equals(MouseEvents.MOUSE_DOUBLECLICK)) {
			doAction();
		} else {
			if (mouseEvent.equals(MouseEvents.MOUSE_CLICK)) {
				doAction();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (mouseEvent.equals(MouseEvents.MOUSE_IN)) {
			doAction();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (mouseEvent.equals(MouseEvents.MOUSE_OUT)) {
			doAction();
		}
	}

}

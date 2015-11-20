package allgemein;

import java.awt.event.InputEvent;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public enum JavaFXKeyEventType {

	ENTER(KeyCode.ENTER), DOWN(KeyCode.DOWN), UP(KeyCode.UP), F1(
			KeyCode.F1), F2(KeyCode.F2), F3(KeyCode.F3), F4(
			KeyCode.F4), F5(KeyCode.F5), F6(KeyCode.F6), F7(
			KeyCode.F7), F8(KeyCode.F8), F9(KeyCode.F9), F10(
			KeyCode.F10), F11(KeyCode.F11), F12(KeyCode.F12), CTRL_D(
			KeyCode.D, InputEvent.CTRL_MASK), CTRL_ENTER(KeyCode.ENTER,
			InputEvent.CTRL_MASK), COPY(KeyCode.INSERT,
			InputEvent.CTRL_MASK), PAST(KeyCode.INSERT,
			InputEvent.SHIFT_MASK), ;

	private final KeyCode ev;
	private int mask=0;

	private JavaFXKeyEventType(KeyCode ev, int mask) {
		this.ev = ev;
		this.mask = mask;
	}

	private JavaFXKeyEventType(KeyCode ev) {
		this(ev, 0);
	}

	public KeyCode getKeyEvent() {
		return ev;
	}

	public int getMask() {
		return mask;
	}

}

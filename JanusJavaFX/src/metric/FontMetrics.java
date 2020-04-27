package metric;

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontMetrics {
	private Text text;

	public FontMetrics(Font fnt) {
		text = new Text();
		text.setFont(fnt);
	}

	public Bounds bounds(String txt) {
		text.setText(txt);
		return  text.getLayoutBounds();
	}


}

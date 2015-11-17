package probe;

public class ColorAWT2Fx {
	public static javafx.scene.paint.Color convert(java.awt.Color color) {
		return javafx.scene.paint.Color.color((color.getRed())/ 255.0,(color.getGreen())/ 255.0,(color.getBlue())/ 255.0);
	}
	
	
	public static  javafx.scene.text.Font convert(java.awt.Font font) {
		return javafx.scene.text.Font.font(font.getFontName(),font.getSize2D());
	}
}

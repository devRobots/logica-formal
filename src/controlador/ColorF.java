package controlador;

import javafx.scene.paint.Color;

public class ColorF {
	
	private Color color;
	private String value;
	
	
	public ColorF(Color color, String value) {
		this.color = color;
		this.value = value;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}

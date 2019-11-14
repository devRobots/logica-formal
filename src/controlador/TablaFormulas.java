package controlador;

import javafx.scene.control.Button;

public class TablaFormulas {
	
	String formula;
	Button boton;

	public TablaFormulas(String formula,Button boton) {
		super();
		this.formula = formula;
		this.boton = boton;
	}
	
	public Button getBoton() {
		return boton;
	}

	public void setBoton(Button boton) {
		this.boton = boton;
	}


	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String getId() {
		return boton.getId();
	}
	
}

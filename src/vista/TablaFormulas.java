package vista;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class TablaFormulas {

	String formula;
	Button boton;
	CheckBox check;

	public TablaFormulas(String formula, Button boton, CheckBox check) {
		super();
		this.formula = formula;
		this.boton = boton;
		this.check = check;
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

	public CheckBox getCheck() {
		return check;
	}

	public void setCheck(CheckBox check) {
		this.check = check;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TablaFormulas other = (TablaFormulas) obj;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		return true;
	}

	
	

}

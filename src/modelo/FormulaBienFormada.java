package modelo;

import java.util.ArrayList;
import java.util.Collections;
import modelo.Operadores;

public class FormulaBienFormada {
	private String fbf;
	private FormaNormal forma;

	private ArrayList<Character> atomos;

	public FormulaBienFormada(String fbf) {
		this.fbf = fbf;

		atomos = new ArrayList<Character>();

		for (int i = 0; i < fbf.length(); i++) {
			char c = fbf.charAt(i);

			if (Character.isAlphabetic(c)) {
				if (!atomos.contains(c)) {
					atomos.add(c);
				}
			}
		}

		Collections.sort(atomos);

		if (esFND(fbf)) {
			forma = FormaNormal.FND;
		} else if (esFNC(fbf)) {
			forma = FormaNormal.FNC;
		} else {
			forma = FormaNormal.NINGUNO;
		}
	}

	public FormulaBienFormada toFND() {
		FormulaBienFormada fnd = null;

		if (forma == FormaNormal.FND) {
			fnd = this;
		} else {
			String fbfFnd = null;
			fnd = new FormulaBienFormada(fbfFnd);
		}

		return fnd;
	}

	private boolean esFND(String fbf) {
		boolean flag = false;

		return flag;
	}

	public FormulaBienFormada toFNC() {
		FormulaBienFormada fnc = null;

		if (forma == FormaNormal.FNC) {
			fnc = this;
		} else {
			String fbfFnc = null;
			fnc = new FormulaBienFormada(fbfFnc);
		}

		return fnc;
	}

	private boolean esFNC(String fbf) {
		boolean flag = false;

		return flag;
	}
	
	private FormulaBienFormada simplificarFormaNormal(FormulaBienFormada formula) {
		FormulaBienFormada fn = new FormulaBienFormada(formula.getFbf());
		
		if (fn.contains(Operadores.EQUIVALENCIA)) {
			fn = aplicarAxioma(fn, 9);
		}
		
		return fn;
	}

	public ArrayList<String> toFC() throws Exception {
		if (forma != FormaNormal.FNC) {
			throw new Exception("No es una FNC");
		}

		ArrayList<String> fc = new ArrayList<String>();

		return fc;
	}

	private int indiceDeOperadorPrincipal(String fbf) {
		int indice = -1;

		if (fbf.startsWith("¬")) {
			indice = 0;
		} else {
			int contParentesis = 0;

			for (int i = 0; i < fbf.length() && indice == -1; i++) {
				char c = fbf.charAt(i);

				if (c == '(') {
					contParentesis++;
				} else if (c == ')') {
					contParentesis--;
				} else {
					if (contParentesis == 0) {
						indice = i;
					}
				}
			}
		}

		return indice;
	}

	private FormulaBienFormada aplicarAxioma(FormulaBienFormada fbf, int axioma) {
		FormulaBienFormada salida = null;

		switch (axioma) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		default:
			return null;
		}

		return salida;
	}
	
	public boolean contains(String elemento) {
		return fbf.contains(elemento);
	}

	public String getFbf() {
		return fbf;
	}

	public void setFbf(String fbf) {
		this.fbf = fbf;
	}

	public FormaNormal getForma() {
		return forma;
	}

	public void setForma(FormaNormal forma) {
		this.forma = forma;
	}

	public ArrayList<Character> getAtomos() {
		return atomos;
	}
}

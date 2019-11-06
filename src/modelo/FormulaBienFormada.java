package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class FormulaBienFormada {
	private String fbf;
	private ArrayList<Character> atomos;
	private ArbolFormula arbol;

	public FormulaBienFormada(String fbf) {
		atomos = new ArrayList<>();

		for (int i = 0; i < fbf.length(); i++) {
			char c = fbf.charAt(i);

			if (Character.isAlphabetic(c)) {
				if (!atomos.contains(c)) {
					atomos.add(c);
				}
			}
		}

		Collections.sort(atomos);

		arbol = new ArbolFormula(fbf);
		this.fbf = fbf;
	}

	private String toFNC() {
		String fnc = fbf;

		if (fnc.contains(Operadores.EQUIVALENCIA)) {
			fnc = axioma9(fnc);
		}
		if (fnc.contains(Operadores.CONDICIONAL)) {
			fnc = axioma8(fnc);
		}

		fnc = axioma7(fnc);

		if (fnc.contains(Operadores.NEGACION + Operadores.NEGACION)) {
			fnc = axioma4(fnc);
		}

		fnc = aplicarAsociativa(fnc);

		return fnc;
	}

	private String aplicarAsociativa(String fnc) {
		// TODO Auto-generated method stub
		return null;
	}

	private String axioma4(String fnc) {
		String salida = fnc;

		salida.replace(Operadores.NEGACION + "(" + Operadores.NEGACION, "");

		return salida;
	}

	private String axioma7(String fnc) {
		// TODO Auto-generated method stub
		return null;
	}

	private String axioma8(String fnc) {
		String salida = fnc;

		while (salida.contains(Operadores.CONDICIONAL)) {

		}

		return salida;
	}

	private String axioma9(String fnc) {
		String salida = fnc;

		while (salida.contains(Operadores.EQUIVALENCIA)) {

		}

		return salida;
	}

	private boolean esFNC(String fbf) {
		boolean flag = false;

		int indice = fbf.charAt(indiceDeOperadorPrincipal(fbf));

		if (indice != 0) {
			String operadorPrincipal = fbf.substring(indice, indice + 1);

			if (operadorPrincipal == Operadores.CONJUNCION) {
				flag = true;
			}
		}

		return flag;
	}

	public ArrayList<String> toFC() {
		ArrayList<String> fcs = new ArrayList<>();
		String[] fds = toFNC().split(Operadores.CONJUNCION);

		for (String fd : fds) {
			String fc = fd.replace(Operadores.DISYUNCION, "");
			fc = fc.replace("(", "");
			fc = fc.replace(")", "");

			fcs.add(fc);
		}

		return fcs;
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

	public String getFbf() {
		return fbf;
	}

	public ArrayList<Character> getAtomos() {
		return atomos;
	}

	/**
	 * @return the arbol
	 */
	public ArbolFormula getArbol() {
		return arbol;
	}

	/**
	 * @param arbol the arbol to set
	 */
	public void setArbol(ArbolFormula arbol) {
		this.arbol = arbol;
	}

}

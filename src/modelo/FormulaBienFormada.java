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

	public String toFNC() {
		String fnc = fbf;

		if (fnc.contains(Operadores.EQUIVALENCIA)) {
			fnc = axioma9(fnc);
		}
		if (fnc.contains(Operadores.CONDICIONAL)) {
			fnc = axioma8(fnc);
		}

		fnc = axioma4(fnc);
		fnc = axioma7(fnc);
		fnc = axioma4(fnc);

		fnc = axioma5(fnc);

		fnc = axioma6(fnc);

		return fnc;
	}

	private String axioma4(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(salida);

		Nodo actual = arbol.getNodoNegado();

		while (actual != null) {
			actual.setValor(actual.getIzquierdo().getIzquierdo().getValor());
			actual.setFbf(actual.getIzquierdo().getIzquierdo().getFbf());

			if (!actual.esAtomo()) {;
				Nodo izq = actual.getIzquierdo().getIzquierdo().getIzquierdo();
				Nodo der = actual.getIzquierdo().getIzquierdo().getDerecho();
				
				actual.setIzquierdo(izq);
				actual.setDerecho(der);
			} else {
				actual.setIzquierdo(null);
			}

			actual = arbol.getNodoNegado();
		}

		salida = arbol.toString();
		return salida;
	}

	private String axioma5(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(salida);

		Nodo actual = arbol.getNodoDuplicado();

		while (actual != null) {
			actual.setValor(actual.getIzquierdo().getValor());

			Nodo phi = actual.getIzquierdo().getIzquierdo();
			Nodo psi = actual.getIzquierdo().getDerecho();

			actual.setIzquierdo(phi);
			actual.setDerecho(psi);

			actual = arbol.getNodoDuplicado();
		}

		salida = arbol.toString();
		return salida;
	}

	private String axioma6(String fnc) {
		String salida = fnc;

		return fnc;
	}

	private String axioma7(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(fnc);

		Nodo actual = arbol.getNodoMorgan();

		while (actual != null) {
			if (actual.getIzquierdo().getValor() == Operadores.DISYUNCION.charAt(0)) {
				actual.setValor(Operadores.CONJUNCION.charAt(0));
			} else {
				actual.setValor(Operadores.DISYUNCION.charAt(0));
			}

			String phi = actual.getIzquierdo().getIzquierdo().getFbf();
			String psi = actual.getIzquierdo().getDerecho().getFbf();

			actual.setIzquierdo(new Nodo(Operadores.NEGACION + "(" + phi + ")"));
			actual.setDerecho(new Nodo(Operadores.NEGACION + "(" + psi + ")"));

			actual = arbol.getNodoMorgan();
			salida = arbol.toString();
		}

		return salida;
	}

	private String axioma8(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(fnc);

		while (arbol.contains(Operadores.CONDICIONAL.charAt(0))) {
			Nodo actual = arbol.find(Operadores.CONDICIONAL.charAt(0));

			String phi = actual.getIzquierdo().getFbf();

			actual.setValor(Operadores.DISYUNCION.charAt(0));
			actual.setIzquierdo(new Nodo(Operadores.NEGACION + "(" + phi + ")"));

			salida = arbol.toString();
		}

		return salida;
	}

	private String axioma9(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(fnc);

		while (arbol.contains(Operadores.EQUIVALENCIA.charAt(0))) {
			Nodo actual = arbol.find(Operadores.EQUIVALENCIA.charAt(0));

			String phi = actual.getIzquierdo().getFbf();
			String psi = actual.getDerecho().getFbf();

			actual.setValor(Operadores.CONJUNCION.charAt(0));
			actual.setIzquierdo(new Nodo("(" + phi + ")" + Operadores.CONDICIONAL + "(" + psi + ")"));
			actual.setDerecho(new Nodo("(" + psi + ")" + Operadores.CONDICIONAL + "(" + phi + ")"));

			salida = arbol.toString();
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

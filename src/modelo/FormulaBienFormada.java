package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class FormulaBienFormada {
	public String fbf;
	public ArrayList<Character> atomos;
	public ArbolFormula arbol;
	public ArrayList<String> procedimiento;

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

	public ArrayList<String> getProcedimiento() {
		return procedimiento;
	}

	public String toFNC() {
		String fnc = fbf;
		procedimiento = new ArrayList<String>();
		
		if (fnc.contains(Operadores.EQUIVALENCIA)) {
			fnc = axioma9(fnc);
			procedimiento.add(fnc+" ... Axioma 9");
		}
		if (fnc.contains(Operadores.CONDICIONAL)) {
			fnc = axioma8(fnc);
			procedimiento.add(fnc+" ... Axioma 8");
		}
		
		fnc = axioma4(fnc);
		procedimiento.add(fnc+" ... Axioma 4");
		
		fnc = axioma7(fnc);
		procedimiento.add(fnc+" ... Axioma 7");
		
		fnc = axioma4(fnc);
		procedimiento.add(fnc+" ... Axioma 4");

		fnc = axioma5(fnc);
		procedimiento.add(fnc+" ... Axioma 5");

		fnc = axioma3(fnc);
		procedimiento.add(fnc+" ... Axioma 3");

		return fnc;
	}

	public String axioma3(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(salida);

		Nodo actual = arbol.getNodoDisyuncionConjuncion();

		while (actual != null) {
			if (actual.getIzquierdo().getValor() == Operadores.CONJUNCION.charAt(0)) {
				Nodo izq = new Nodo(Operadores.DISYUNCION.charAt(0));
				izq.setIzquierdo(new Nodo(actual.getIzquierdo().getIzquierdo().getFbf()));
				izq.setDerecho(actual.getDerecho());

				Nodo der = new Nodo(Operadores.DISYUNCION.charAt(0));
				der.setIzquierdo(new Nodo(actual.getIzquierdo().getDerecho().getFbf()));
				der.setDerecho(actual.getDerecho());

				actual.setIzquierdo(izq);
				actual.setDerecho(der);
				
				actual.setValor(Operadores.CONJUNCION.charAt(0));

			} else if(actual.getDerecho().getValor() == Operadores.CONJUNCION.charAt(0)){
				Nodo izq = new Nodo(Operadores.DISYUNCION.charAt(0));
				izq.setIzquierdo(new Nodo(actual.getDerecho().getIzquierdo().getFbf()));
				izq.setDerecho(actual.getIzquierdo());

				Nodo der = new Nodo(Operadores.DISYUNCION.charAt(0));
				der.setIzquierdo(new Nodo(actual.getDerecho().getDerecho().getFbf()));
				der.setDerecho(actual.getIzquierdo());

				actual.setIzquierdo(izq);
				actual.setDerecho(der);
				
				actual.setValor(Operadores.CONJUNCION.charAt(0));
				
			}
			actual = arbol.getNodoDisyuncionConjuncion();
			
		}

		salida = arbol.toString();
		return salida;
	}

	public String axioma4(String fnc) {
		String salida = fnc;
		ArbolFormula arbol = new ArbolFormula(salida);

		Nodo actual = arbol.getNodoNegado();

		while (actual != null) {
			actual.setValor(actual.getIzquierdo().getIzquierdo().getValor());
			actual.setFbf(actual.getIzquierdo().getIzquierdo().getFbf());

			if (!actual.esAtomo()) {
				;
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

	public String axioma5(String fnc) {
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

	public String axioma6(String fnc) {
		String salida = fnc;

		ArbolFormula arbol = new ArbolFormula(fnc);

		Nodo actual = arbol.getNodoConjuncion();

		while (actual != null) {

		}

		return fnc;
	}

	public String axioma7(String fnc) {
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

	public String axioma8(String fnc) {
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

	public String axioma9(String fnc) {
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

	public boolean esFNC(String fbf) {
		boolean flag = true;
		ArbolFormula arbol = new ArbolFormula(fbf);
		Nodo nodo = arbol.getRaiz();

		if (nodo.getValor() == Operadores.CONJUNCION.charAt(0)) {

			flag = esFNC(nodo, true);

		} else {

			flag = false;

		}
		return flag;
	}

	private boolean esFNC(Nodo nodo, boolean isConjuncion) {
		if (nodo.esAtomo()) {
			return true;
		}
		if (nodo.getValor() == Operadores.DISYUNCION.charAt(0) && isConjuncion) {
			isConjuncion = false;
		} else if (nodo.getValor() == Operadores.CONJUNCION.charAt(0) && !isConjuncion
				|| nodo.getValor() == Operadores.CONDICIONAL.charAt(0)
				|| nodo.getValor() == Operadores.EQUIVALENCIA.charAt(0)) {
			return false;
		}

		return esFNC(nodo.getIzquierdo(), isConjuncion) && esFNC(nodo.getDerecho(), isConjuncion);

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

	public int indiceDeOperadorPrincipal(String fbf) {
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

package modelo;

import java.util.ArrayList;

public class ArbolFormula {
	private Nodo raiz;

	public ArbolFormula(String fbf) {
		raiz = new Nodo(fbf);
	}

	public ArrayList<Nodo> findAll(char caracter) {
		ArrayList<Nodo> coincidencias = new ArrayList<>();
		findAll(raiz, caracter, coincidencias);
		return coincidencias;
	}

	private void findAll(Nodo nodo, char caracter, ArrayList<Nodo> coincidencias) {
		if (raiz == null) {
			return;
		}

		if (nodo.getValor() == caracter) {
			coincidencias.add(nodo);
		}

		findAll(nodo.getIzquierdo(), caracter, coincidencias);
		findAll(nodo.getDerecho(), caracter, coincidencias);
	}

	public Nodo find(char caracter) {
		return find(raiz, caracter);
	}

	private Nodo find(Nodo nodo, char caracter) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == caracter) {
			return nodo;
		}

		Nodo izq = find(nodo.getIzquierdo(), caracter);
		Nodo der = find(nodo.getDerecho(), caracter);

		if (izq != null) {
			return izq;
		} else {
			return der;
		}
	}

	public Nodo getNodoMorgan() {
		return getNodoMorgan(raiz);
	}

	private Nodo getNodoMorgan(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == Operadores.NEGACION.charAt(0)) {
			if (nodo.getIzquierdo().getValor() == Operadores.DISYUNCION.charAt(0) || nodo.getIzquierdo().getValor() == Operadores.CONJUNCION.charAt(0)) {
				return nodo;
			}
		}
		
		Nodo izq = getNodoMorgan(nodo.getIzquierdo());
		Nodo der = getNodoMorgan(nodo.getDerecho());

		if (izq != null) {
			return izq;
		} else {
			return der;
		}
	}

	public Nodo getNodoNegado() {
		return getNodoNegado(raiz);
	}

	private Nodo getNodoNegado(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == Operadores.NEGACION.charAt(0)) {
			if (nodo.getIzquierdo().getValor() == Operadores.NEGACION.charAt(0)) {
				return nodo;
			}
		}
		
		Nodo izq = getNodoNegado(nodo.getIzquierdo());
		Nodo der = getNodoNegado(nodo.getDerecho());

		if (izq != null) {
			return izq;
		} else {
			return der;
		}
	}
	
	public boolean contains(char valor) {
		return contains(raiz, valor);
	}
	
	private boolean contains(Nodo nodo, char valor) {
		if (nodo == null) {
			return false;
		}
		
		if (nodo.getValor() == valor) {
			return true;
		}

		boolean izq = contains(nodo.getIzquierdo(), valor);
		boolean der = contains(nodo.getDerecho(), valor);
		
		if (izq) {
			return izq;
		}
		else {
			return der;
		}
	}

	@Override
	public String toString() {
		return raiz.toString();
	}

	/**
	 * @return the raiz
	 */
	public Nodo getRaiz() {
		return raiz;
	}

	/**
	 * @param raiz the raiz to set
	 */
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

}

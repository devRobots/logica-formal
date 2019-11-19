/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Logica Formal
 * Ejercicio: Programa FNC, FND, Reslución
 * @author : Brayan Tabares Hidalgo - Yesid Rosas Toro - Samara Rincón Montaña
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package modelo;

import java.util.ArrayList;

public class ArbolFormula {
	private Nodo raiz;

	/**
	 * Constructor
	 * @param fbf
	 */
	public ArbolFormula(String fbf) {
		raiz = new Nodo(fbf);
	}
	
	public ArbolFormula() {
		raiz=null;
	}

	/**
	 * Agrega un nodo
	 * @param nodo
	 * @param fnc
	 */
	public void addNodo(Nodo nodo, boolean fnc) {
		char operador;
		if(fnc) {
			operador=Operadores.CONJUNCION.charAt(0);
		}else {
			operador=Operadores.DISYUNCION.charAt(0);
		}
		if (raiz == null) {
			raiz = nodo;
		} else if (nodo.getValor()==operador) {
			nodo.setIzquierdo(raiz);
			raiz=nodo;
		} else  {
			if(raiz.getValor()==operador) {
				if(raiz.getDerecho()==null){
					raiz.setDerecho(nodo);
				}else {
					raiz.getDerecho().addNodo(nodo);
				}
			}else {
				raiz.addNodo(nodo);
			}
		}
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
			if (nodo.getIzquierdo().getValor() == Operadores.DISYUNCION.charAt(0)
					|| nodo.getIzquierdo().getValor() == Operadores.CONJUNCION.charAt(0)) {
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

	public Nodo getNodoConjuncion() {
		return getNodoConjuncion(raiz);
	}

	private Nodo getNodoConjuncion(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == Operadores.CONJUNCION.charAt(0)) {
			if (nodo.getIzquierdo() != null) {
				if (nodo.getIzquierdo().getValor() == Operadores.CONJUNCION.charAt(0)) {
					return nodo;
				}
			}
		}

		Nodo izq = getNodoConjuncion(nodo.getIzquierdo());
		Nodo der = getNodoConjuncion(nodo.getDerecho());

		if (izq != null) {
			return izq;
		} else {
			return der;
		}
	}

	public Nodo getNodoDisyuncion() {
		return getNodoDisyuncion(raiz);
	}

	private Nodo getNodoDisyuncion(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == Operadores.DISYUNCION.charAt(0)) {
			if (nodo.getIzquierdo() != null) {
				if (nodo.getIzquierdo().getValor() == Operadores.DISYUNCION.charAt(0)) {
					return nodo;
				}
			}
		}

		Nodo izq = getNodoDisyuncion(nodo.getIzquierdo());
		Nodo der = getNodoDisyuncion(nodo.getDerecho());

		if (izq != null) {
			return izq;
		} else {
			return der;
		}
	}

	public Nodo getNodoConjuncionDisyuncion() {
		return getNodoConjuncionDisyuncion(raiz);
	}

	private Nodo getNodoConjuncionDisyuncion(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == Operadores.CONJUNCION.charAt(0)) {
			if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getValor() == Operadores.DISYUNCION.charAt(0))||
					(nodo.getDerecho() != null && nodo.getDerecho().getValor() == Operadores.DISYUNCION.charAt(0))	) {
					return nodo;
			}
		}
		Nodo izq = getNodoConjuncionDisyuncion(nodo.getIzquierdo());
		Nodo der = getNodoConjuncionDisyuncion(nodo.getDerecho());

		if (izq != null) {
			return izq;
		} else {
			return der;
		}
	}

	public Nodo getNodoDisyuncionConjuncion() {
		return getNodoDisyuncionConjuncion(raiz);
	}

	private Nodo getNodoDisyuncionConjuncion(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getValor() == Operadores.DISYUNCION.charAt(0)) {
			if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getValor() == Operadores.CONJUNCION.charAt(0))||
					(nodo.getDerecho() != null && nodo.getDerecho().getValor() == Operadores.CONJUNCION.charAt(0))	) {
					return nodo;
			}
		}

		Nodo izq = getNodoDisyuncionConjuncion(nodo.getIzquierdo());
		Nodo der = getNodoDisyuncionConjuncion(nodo.getDerecho());

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
			if (nodo.getIzquierdo() != null) {
				if (nodo.getIzquierdo().getValor() == Operadores.NEGACION.charAt(0)) {
					return nodo;
				}
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

	public Nodo getNodoDuplicado() {
		return getNodoDuplicado(raiz);
	}

	private Nodo getNodoDuplicado(Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		if (nodo.getIzquierdo() == null) {
			return null;
		}

		if (nodo.getIzquierdo().equals(nodo.getDerecho())) {
			return nodo;
		}

		Nodo izq = getNodoDuplicado(nodo.getIzquierdo());
		Nodo der = getNodoDuplicado(nodo.getDerecho());

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
		} else {
			return der;
		}
	}

	@Override
	public String toString() {
		if(raiz!=null) {
			return raiz.toString();
		}else {
			return "";
		}
		
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

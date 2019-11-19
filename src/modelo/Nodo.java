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

public class Nodo {

	private char valor;
	private String fbf;
	private Nodo izquierdo;
	private Nodo derecho;

	public Nodo(char valor) {
		this.valor = valor;
		fbf = String.valueOf(valor);
	}
	
	public Nodo(Nodo nodo) {
		super();
		this.valor = nodo.getValor();
		this.fbf = nodo.getFbf();
		this.izquierdo = nodo.getIzquierdo();
		this.derecho = nodo.getDerecho();
	}
	
	public void resetNodo(Nodo nodo) {
		this.valor = nodo.getValor();
		this.fbf = nodo.getFbf();
		this.izquierdo = nodo.getIzquierdo();
		this.derecho = nodo.getDerecho();
	}

	public Nodo(String fbf) {
		if (fbf.length() == 1) {
			valor = fbf.charAt(0);
		} else if(fbf.length()!=0){
			int indice = indiceDeOperadorPrincipal(fbf);
			valor = fbf.charAt(indice);

			if (indice == 0) {
				izquierdo = new Nodo(fbf.substring(2, fbf.length() - 1));
			} else {
				izquierdo = new Nodo(fbf.substring(1, indice - 1));
				derecho = new Nodo(fbf.substring(indice + 2, fbf.length() - 1));
			}
		}

		this.fbf = fbf;
	}

	public void addNodo(Nodo nodo) {
		if((esAtomo()||getValor()==Operadores.NEGACION.charAt(0)||derecho!=null)&&!(nodo.esAtomo()||nodo.getValor()==Operadores.NEGACION.charAt(0))) {
			nodo.setIzquierdo(new Nodo(this));
			resetNodo(nodo);	
		}else if(nodo.esAtomo()||nodo.getValor()==Operadores.NEGACION.charAt(0)) {
			derecho=nodo;	
		}
	}

	private int indiceDeOperadorPrincipal(String fbf) {
		int indice = -1;

		if (fbf.startsWith(Operadores.NEGACION)) {
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

	public boolean esAtomo() {
		if (valor == Operadores.CONDICIONAL.charAt(0)) {
			return false;
		} else if (valor == Operadores.CONJUNCION.charAt(0)) {
			return false;
		} else if (valor == Operadores.DISYUNCION.charAt(0)) {
			return false;
		} else if (valor == Operadores.EQUIVALENCIA.charAt(0)) {
			return false;
		} else if (valor == Operadores.NEGACION.charAt(0)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return the valor
	 */
	public char getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(char valor) {
		this.valor = valor;
		fbf=toString();
	}

	/**
	 * @return the izquierdo
	 */
	public Nodo getIzquierdo() {
		return izquierdo;
	}

	/**
	 * @param izquierdo the izquierdo to set
	 */
	public void setIzquierdo(Nodo izquierdo) {
		this.izquierdo = izquierdo;
		fbf=toString();
	}

	/**
	 * @return the derecho
	 */
	public Nodo getDerecho() {
		return derecho;
	}

	/**
	 * @param derecho the derecho to set
	 */
	public void setDerecho(Nodo derecho) {
		this.derecho = derecho;
		fbf=toString();
	}

	public String getFbf() {
		return fbf;
	}

	public void setFbf(String fbf) {
		this.fbf = fbf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (derecho == null ? 0 : derecho.hashCode());
		result = prime * result + (izquierdo == null ? 0 : izquierdo.hashCode());
		result = prime * result + valor;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Nodo other = (Nodo) obj;

		if (!other.fbf.equals(fbf)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {

		if (izquierdo != null) {
			if (derecho != null) {
				return "(" + izquierdo + ")" + valor + "(" + derecho + ")";
			} else {
				return valor + "(" + izquierdo + ")";
			}
		}

		return String.valueOf(valor);
	}
}

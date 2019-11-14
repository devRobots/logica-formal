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

	public Nodo(String fbf) {
		if (fbf.length() == 1) {
			valor = fbf.charAt(0);
		} else {
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
		switch (fbf) {
		case Operadores.CONDICIONAL:
			return false;
		case Operadores.CONJUNCION:
			return false;
		case Operadores.DISYUNCION:
			return false;
		case Operadores.EQUIVALENCIA:
			return false;
		case Operadores.NEGACION:
			return false;
		default:
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
		if (derecho == null) {
			if (other.derecho != null) {
				return false;
			}
		} else if (!derecho.equals(other.derecho)) {
			return false;
		}
		if (izquierdo == null) {
			if (other.izquierdo != null) {
				return false;
			}
		} else if (!izquierdo.equals(other.izquierdo)) {
			return false;
		}
		if (valor != other.valor) {
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
